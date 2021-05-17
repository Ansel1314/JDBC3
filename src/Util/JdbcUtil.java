package Util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author: Ansel
 * @date: 2021/5/11 0011 22:37
 * @description:
 */
public class JdbcUtil {
    /**
     * Druid数据库连接池工具类
     */
    private static DataSource DruidSource;
    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("DruidConfig.properties");
            properties.load(inputStream);
            DruidSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static Connection getConnection3() throws SQLException {
        Connection connection = DruidSource.getConnection();
        return connection;
    }


    /**
     * DBCP数据库连接池工具类
     */

    private static DataSource ds;
    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("DBCPConfig.properties");
            properties.load(inputStream);
            ds = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection2() throws SQLException {
        Connection connection = ds.getConnection();
        return connection;
    }


    /**
     * c3p0连接池工具类
     */
    //注意ComboPooledDataSource放在方面外面，因为每次调用方法都会new一个,连接池只需要new一个就行了，不需要多个
    static ComboPooledDataSource cpds = new ComboPooledDataSource("c3p0");

    public static Connection getConnection1() throws SQLException {
        Connection connection = cpds.getConnection();
        return connection;
    }


    //传统数据库连接
    public static Connection getConnection() throws Exception {
        //1。读取4个基本信息
        InputStream IS = ClassLoader.getSystemClassLoader().getResourceAsStream("JDBC.properties");

        Properties properties = new Properties();
        properties.load(IS);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("DriverClass");

        //2.加载驱动
        Class.forName(driverClass);

        //3.获取连接
        Connection connection = DriverManager.getConnection(url, user, password);

        //返回
        return connection;
    }

    public static void closeResource(Connection connection, Statement statement) {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (statement != null)
                statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //资源关闭的重载
    public static void closeResource(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (statement != null)
                statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeResoure1(Connection connection,Statement ps,ResultSet resultSet){
//        try {
//            DbUtils.close(connection);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        try {
//            DbUtils.close(ps);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        try {
//            DbUtils.close(resultSet);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

        DbUtils.closeQuietly(connection);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(resultSet);


    }
}

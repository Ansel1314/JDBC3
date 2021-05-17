package _3数据库操作PrepareStatement;

import Util.JdbcUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
/*
 *使用PrepareStatement来替代Statement，实现对数据库的增删改查操作
 *
 *
 *
 *
 * */


public class PrepareStatementUpdateTest {
    //测试通用增删改操作的方法
    @Test
    public void testCommonUpdate() {
//        String sql ="delete from customers where id =?";
//        update(sql,3);

        String sql = "update `order` set order_name =? where order_id=?";
        update(sql, "BB", "2");
    }


    //通用增删改操作
    public void update(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JdbcUtil.getConnection();
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
                System.out.println(args[i]);
            }

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, ps);
        }
    }

    //修改一条customer表中一条数据
    @Test
    public void testUpdate() {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JdbcUtil.getConnection();

            String sql = "update customers set name = ? where id = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, "莫扎特");
            ps.setObject(2, 18);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, ps);
        }
    }


    //向customer表中添加一条数据
    @Test
    public void InsertTest() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ParseException {
        InputStream IS = null;
        PreparedStatement ps = null;
        try {
            //1.读取配置文件中的4个基本信息
            IS = PrepareStatementUpdateTest.class.getClassLoader().getResourceAsStream("JDBC.properties");
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

            //4.预编译sql语句,返回PreareStatement实例
            String sql = "insert into customers(name,email,birth)values(?,?,?)";
            ps = connection.prepareStatement(sql);

            //5.填充占位符
            ps.setString(1, "ansel");
            ps.setString(2, "ansel@qq.com");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse("1000-01-01");
            ps.setDate(3, new Date(date.getTime()));

            //6.执行操作
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //7.关闭资源
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (IS != null)
                    IS.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

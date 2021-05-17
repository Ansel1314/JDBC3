package _11数据库连接池连接方式;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author: Ansel
 * @date: 2021/5/12 0012 20:58
 * @description:使用DBCP数据库连接池
 */
public class DBCP {

    //方式一:DBCP数据库连接池的硬编码方式
    @Test
    public void testDBCPGetconnection() throws SQLException {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql:///test");//省略localhost:3306
        source.setUsername("root");
        source.setPassword("huimie");

        source.setInitialSize(10);
        source.setMaxActive(10);

        Connection connection = source.getConnection();
        System.out.println(connection);
    }


    //方式二:使用配置文件
    @Test
    public void testDBCPGetconnection2() throws Exception {
        Properties properties = new Properties();
        //方式一
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("DBCPConfig.properties");
        //方式二:
//        FileInputStream inputStream = new FileInputStream(new File("src\\DBCPConfig.properties"));

        properties.load(inputStream);
        DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}

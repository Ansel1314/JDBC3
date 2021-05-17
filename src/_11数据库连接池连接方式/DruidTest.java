package _11数据库连接池连接方式;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author: Ansel
 * @date: 2021/5/12 0012 23:00
 * @description:Druid数据库连接池
 */
public class DruidTest {
    @Test
    public void testDruid() throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("DruidConfig.properties");
        properties.load(inputStream);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}

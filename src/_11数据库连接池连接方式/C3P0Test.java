package _11数据库连接池连接方式;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.jupiter.api.Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: Ansel
 * @date: 2021/5/10 0010 23:19
 * @description:
 */
public class C3P0Test {
    //方式一:不推荐硬编码的方式连接
    @Test
    public void testGetConnection() throws PropertyVetoException, SQLException {
        //获取C3P0数据库连接池
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        cpds.setUser("root");
        cpds.setPassword("huimie");

        //通过设置相关参数对数据库连接池进行管理
        //设置连接池初始化连接数
        cpds.setInitialPoolSize(10);

        Connection connection = cpds.getConnection();
        System.out.println(connection);

    }

    //方式二:使用配置文件.xml，注意文件名必须为c3p0-config
    @Test
    public void testGetConnection2() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource("c3p0");

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

}

package _1获取数据库连接方式一;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {

    //方式一:
    @Test
    public void testConnection1() throws Exception {

        //获取Driver实现类对象
        Driver driver = new com.mysql.jdbc.Driver();

        //提供要链接的数据库
        String url = "jdbc:mysql://localhost:3306/test";

        //提供链接需要的用户名和密码
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "huimie");

        //获取链接
        Connection connection = driver.connect(url, properties);
        System.out.println(connection);
    }

    //对方式一的迭代:在如下的程序中不会出现第三方的api，使得程序具有更好的移植性
    @Test
    public void testConnection2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        //获取Driver实现类对象:使用反射
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/test";

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "huimie");
        Connection connection = driver.connect(url, properties);

        System.out.println(connection);
    }

    //方式三:使用DriverManager替换Driver
    @Test
    public void testConnection3() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) aClass.newInstance();

        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "huimie";

        //注册驱动
        DriverManager.registerDriver(driver);

        //获取链接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    //方式四：可以只是加载驱动，不用显示的注册驱动，因为反射的时候加载的Driver有一个静态方法包含注册
    @Test
    public void testConnection4() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "huimie";

        //反射加载的时候回执行一个静态方法注册驱动
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
//        Driver driver = (Driver) aClass.newInstance();
//        //注册驱动
//        DriverManager.registerDriver(driver);

        //获取链接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);

    }

    //方式五:通过配置文件的方式获取连接
    @Test
    public void testConnection5() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        //类加载器
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("JDBC.properties");
        Properties properties = new Properties();
        properties.load(is);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("DriverClass");


        //加载驱动
        Class<?> aClass = Class.forName(driverClass);
        Driver driver = (Driver) aClass.newInstance();


        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);


    }
}

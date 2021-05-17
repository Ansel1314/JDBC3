package _11数据库连接池连接方式;

import Util.JdbcUtil;
import _3数据库操作PrepareStatement.Customers;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author: Ansel
 * @date: 2021/5/12 0012 23:30
 * @description: commons-dbutils 是Apache组织提供的一个开源JDBC工具类库，封装了针对于数据库的增删改查操作
 */
public class DBUtils {

    //测试插入
    @Test
    public void testInsert(){
        Connection connection3=null;
        try {
            QueryRunner runner = new QueryRunner();
             connection3 = JdbcUtil.getConnection3();
            String sql = "insert into customers(name,email,birth)values (?,?,?)";
            int update = runner.update(connection3, sql, "欣欣向荣", "xiangrong@qq.com", "2000-10-10");

            System.out.println("添加了"+update+"条数据");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection3,null);
        }
    }


    //测试查询
    /**
     *BeanHander:是ResultSetHandler接口的实现类，用于封装表中的一条记录
     */
    @Test
    public void testQuery1() throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection connection = JdbcUtil.getConnection3();
        String sql = "select id,name,email,birth from customers where id=?";
        BeanHandler<Customers> beanHandler=new BeanHandler<>(Customers.class);

        Customers query = runner.query(connection, sql, beanHandler,30);
        System.out.println(query);
    }

    //查询多条信息
    @Test
    public void testQuery2() {
        Connection connection=null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JdbcUtil.getConnection3();
            String sql="select id,name,email,birth from customers where id<?";
            BeanListHandler<Customers> beanListHandler = new BeanListHandler<>(Customers.class);

            List<Customers> query = runner.query(connection, sql, beanListHandler, 5);
            query.forEach(System.out::println);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection,null);
        }
    }

    /**
     * MapHandler:是ResultSetHandler接口的实现类,对应表中的一条记录
     * 将字段及相应字段的值作为map中的key和value
     */
    @Test
    public void testQuery3() {
        Connection connection=null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JdbcUtil.getConnection3();
            String sql="select id,name,email,birth from customers where id<?";
            MapHandler mapHandler = new MapHandler();

            Map<String, Object> query = runner.query(connection, sql, mapHandler, 5);
            System.out.println(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection,null);
        }
    }

    /**
     * MapHandler:是ResultSetHandler接口的实现类,对应表中的多条记录
     * 将字段及相应字段的值作为map中的key和value，将这些map添加到List中
     */
    @Test
    public void testQuery4() {
        Connection connection=null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JdbcUtil.getConnection3();
            String sql="select id,name,email,birth from customers where id<?";
            MapListHandler mapHandler = new MapListHandler();

            List<Map<String, Object>> query = runner.query(connection, sql, mapHandler, 5);
            query.forEach(System.out::println);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection,null);
        }
    }

    //查询特殊值使用ScalarHandler
    @Test
    public void testQuery5(){
        Connection connection=null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JdbcUtil.getConnection3();
            String sql="select count(*) from customers";
            ScalarHandler scalarHandler = new ScalarHandler();

            Long count = (Long) runner.query(connection, sql, scalarHandler);
            System.out.println(count);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection,null);
        }
    }

    //自定义ResultSetHandler的实现类
    @Test
    public void testQuery6(){
        Connection connection=null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JdbcUtil.getConnection3();
            String sql="select id,name,email,birth from customers where id =?";

            ResultSetHandler<Customers> handler= new ResultSetHandler<Customers>() {
                @Override
                public Customers handle(ResultSet resultSet) throws SQLException {
                    if (resultSet.next()) {
                        String name = resultSet.getString("name");
                        int id = resultSet.getInt("id");
                        String email = resultSet.getString("email");
                        Date birth = resultSet.getDate("birth");
                        Customers customers = new Customers(id,name,email,birth);
                    return customers;
                    }
                    return null;
                }
            };
            Customers query = runner.query(connection,sql, handler, 30);
            System.out.println(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection,null);
        }
    }
}

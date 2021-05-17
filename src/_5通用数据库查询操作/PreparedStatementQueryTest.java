package _5通用数据库查询操作;

import Util.JdbcUtil;
import _3数据库操作PrepareStatement.Customers;
import _4数据库操作.Order;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @author: Ansel
 * @date: 2021/5/3 0003 21:39
 * @description:通用的查询操作
 */
public class PreparedStatementQueryTest {

    //测试
    @Test
    public void TestCommonOperations() {
        String sql1 = "select id,name,email,birth from customers where id =?";
        Customers customers = getInstance(Customers.class, sql1, 1);
        System.out.println(customers);

        //注意order要使用`order`
        String sql2 = "select order_id orderid,order_name ordername,order_date orderdate from `order` where order_id =?";
        Order order = getInstance(Order.class, sql2, 2);
        System.out.println(order);
    }

    //针对于不同的表查询的通用方法，返回表中的一条记录
    public <T> T getInstance(Class<T> tClass, String sql, Object... agrs) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            //连接数据库
            connection = JdbcUtil.getConnection();
            //预编译sql语句
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < agrs.length; i++) {
                ps.setObject(i + 1, agrs[i]);
            }

            //执行sql,返回一个结果集
            resultSet = ps.executeQuery();
            //获取元数据检索此 ResultSet对象的列的数量，类型和属性。
            ResultSetMetaData rsmd = resultSet.getMetaData();
            //获取ResultSet对象中的列数。
            int columnCount = rsmd.getColumnCount();

            if (resultSet.next()) {
                T t = tClass.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnvalue = resultSet.getObject(i + 1);

                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field declaredField = tClass.getDeclaredField(columnLabel);

                    declaredField.setAccessible(true);
                    declaredField.set(t, columnvalue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, ps, resultSet);
        }
        return null;
    }
}

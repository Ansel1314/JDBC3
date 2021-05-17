package _4数据库操作;

import Util.JdbcUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @author: Ansel
 * @date: 2021/5/3 0003 20:28
 * @description:
 */
public class OrderForQuery {

    /**
     * 针对于表的字段名与属性名不相同的情况
     * 1.必须声明sql时，使用类的属性名来命名字段的别名
     * 2.使用ResultSetMetaData时，需要使用getColumnLabel来代替getColumnNama获取列的别名
     * 说明:如果sql没有给字段起别名，getColumnLabel获取的就是列名
     */

    //针对order表的通用操作
    @Test
    public void testOrderQuery() {
        String sql = "select order_id orderid,order_name ordername,order_date orderdate from `order` where order_id =?";
        Order order = orderForQuery(sql, 2);
        System.out.println(order);
    }

    public Order orderForQuery(String sql, Object... agrs) {
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
                Order order = new Order();
                for (int i = 0; i < columnCount; i++) {
                    Object columnvalue = resultSet.getObject(i + 1);

//                    String columnName = rsmd.getColumnName(i + 1);
//                    Field declaredField = Order.class.getDeclaredField(columnName);

                    //当sql语句中列名使用别名时，使用getColumnLabel
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field declaredField = Order.class.getDeclaredField(columnLabel);

                    declaredField.setAccessible(true);
                    declaredField.set(order, columnvalue);
                }
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, ps, resultSet);
        }
        return null;
    }
}

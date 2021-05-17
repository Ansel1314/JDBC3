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
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Ansel
 * @date: 2021/5/4 0004 15:49
 * @description:针对不同表的多个数据de 通用查询
 */
public class PreparedStatementQueryTest2 {
    //测试
    @Test
    public void TestQuery() {
        //查询customers表中的一些数据
        String sql = "select id,name,email,birth from customers where id <=?";
        List<Customers> forQueryList = getForQueryList(Customers.class, sql, 5);
        forQueryList.forEach(System.out::println);

        //查询
        String sql1 = "select order_id orderid,order_name ordername,order_date orderdate from `order` where order_id<?";
        List<Order> forQueryList1 = getForQueryList(Order.class, sql1, 4);
        forQueryList1.forEach(System.out::println);

    }

    public <T> List<T> getForQueryList(Class<T> tClass, String sql, Object... agrs) {
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

            //创建集合用于存储多个查询结果
            ArrayList<T> ts = new ArrayList<>();
            while (resultSet.next()) {
                T t = tClass.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnvalue = resultSet.getObject(i + 1);

                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field declaredField = tClass.getDeclaredField(columnLabel);

                    declaredField.setAccessible(true);
                    declaredField.set(t, columnvalue);
                }
                ts.add(t);
            }
            return ts;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, ps, resultSet);
        }
        return null;
    }
}

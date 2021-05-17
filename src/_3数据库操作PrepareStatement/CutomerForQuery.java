package _3数据库操作PrepareStatement;

import Util.JdbcUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @author: Ansel
 * @date: 2021/5/2 19:40
 * @description: 针对Customer表的操作
 */
public class CutomerForQuery {
    /**
     * ORM编程思想(object relational mapping)
     * 一个数据表对应一个java类
     * 表中的一条数据对应java中的一个对象
     * 表中的一个字段对应java中的一个属性
     */
    //测试
    @Test
    public void testQueryForCustomers() {
        String sql = "select id,name,email,birth from customers where id = ?";
        Customers customers = queryForCustomers(sql, 12);
        System.out.println(customers);
    }

    //针对Customers表的通用查询操作
    public Customers queryForCustomers(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtil.getConnection();
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            //执行sql语句
            resultSet = ps.executeQuery();
            //获取元数据
            ResultSetMetaData metaData = ps.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = metaData.getColumnCount();
            if (resultSet.next()) {
                Customers customers = new Customers();
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columnvalue = resultSet.getObject(i + 1);
                    //获取列名
                    String columnname = metaData.getColumnName(i + 1);

                    //给Customer指定的对象columnname，赋值为columnvalue通过反射
                    Field field = Customers.class.getDeclaredField(columnname);
                    field.setAccessible(true);
                    field.set(customers, columnvalue);
                }
                return customers;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, ps, resultSet);
        }
        return null;
    }


    @Test
    public void testQuery1() throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtil.getConnection();
            String sql = "select id,name,email,birth from customers where id = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, 2);

            resultSet = ps.executeQuery();


            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

                //方式一:
                //            System.out.println("id="+id+"name="+name+"email="+email+"brith"+brith);

                //方式二:
                //            Object[] objects = new Object[]{id,name,email,brith};
                Customers customers = new Customers(id, name, email, birth);
                System.out.println(customers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, ps, resultSet);
        }
    }
}

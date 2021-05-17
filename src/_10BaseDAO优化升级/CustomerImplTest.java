package _10BaseDAO优化升级;

import Util.JdbcUtil;
import _3数据库操作PrepareStatement.Customers;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: Ansel
 * @date: 2021/5/9 0009 14:22
 * @description:测试DAO
 */
public class CustomerImplTest {
    CustomerImpl customer = new CustomerImpl();

    @Test
    public void testInsert() {
        Connection connection = null;
        try {
            connection = JdbcUtil.getConnection();
            SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = slf.parse("2000-10-10");
            Customers customers = new Customers(1, "ansel4", "ansel@qq.com", new java.sql.Date(date.getTime()));
            customer.insert(connection, customers);
            System.out.println("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, null);
        }
    }

    @Test
    public void testDeleteById() {
        Connection connection = null;
        try {
            connection = JdbcUtil.getConnection();
            int i = customer.deleteById(connection, 19);
            if (i > 0) {
                System.out.println("删除成功");
            } else
                System.out.println("删除失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, null);
        }
    }

    @Test
    public void testUpdate() {
        Connection connection = null;
        try {
            connection = JdbcUtil.getConnection();
            SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = (Date) slf.parse("2000-10-10");
            Customers customers = new Customers(18, "ansel3", "ansel3@qq.com", new java.sql.Date(date.getTime()));
            customer.update(connection, customers);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, null);
        }
    }

    @Test
    public void testGetCustomerById() {
        Connection connection = null;
        try {
            connection = JdbcUtil.getConnection3();
            Customers customerById = customer.getCustomerById(connection, 20);
            System.out.println(customerById);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, null);
        }
    }

    @Test
    public void testGetAll() {
        Connection connection = null;
        try {
            connection = JdbcUtil.getConnection3();
            List<Customers> all = customer.getAll(connection);
            all.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, null);
        }
    }

    @Test
    public void testGetCount() {
        Connection connection = null;
        try {
            connection = JdbcUtil.getConnection();
            Long count = customer.getCount(connection);
            System.out.println(count);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, null);
        }
    }

    @Test
    public void testGetMaxBirth() {
        Connection connection = null;
        try {
            connection = JdbcUtil.getConnection();
            java.sql.Date maxBirth = customer.getMaxBirth(connection);
            String s = maxBirth.toString();
            System.out.println(s);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, null);
        }
    }

}

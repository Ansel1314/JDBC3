package _7blob数据类型;

import Util.JdbcUtil;
import _3数据库操作PrepareStatement.Customers;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;

/**
 * @author: Ansel
 * @date: 2021/5/5 0005 15:09
 * @description: blob数据类型的操作
 */
public class BlobTest {

    //插入blob类型的数据
    @Test
    public void testInsert() {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JdbcUtil.getConnection();
            String sql = "insert into customers(name,email,birth,photo)values(?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, "ansel2");
            ps.setObject(2, "ansel2@qq.com");
            ps.setObject(3, "2000-01-01");

            FileInputStream fis = new FileInputStream("landscape.png");
            ps.setObject(4, fis);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, ps);
        }
    }

    //查询数据库中表customer中Blob类型的字段
    @Test
    public void testQuery() {
        Connection connection = null;
        PreparedStatement ps = null;
        Blob photo1 = null;
        InputStream is = null;
        try {
            connection = JdbcUtil.getConnection();
            String sql = "select id,name,email,birth,photo from customers where id =?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, 22);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                //方式一:
//                int id = resultSet.getInt(1);
//                String name = resultSet.getString(2);
//                String email = resultSet.getString(3);
//                Date birth = resultSet.getDate(4);
                //方式二:
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Date birth = resultSet.getDate("birth");

                Customers customers = new Customers(id, name, email, birth);
                System.out.println(customers);

                photo1 = resultSet.getBlob("photo");
                is = photo1.getBinaryStream();
                FileOutputStream fos = new FileOutputStream("ansel.jpg");
                byte[] bytes = new byte[1024];
                int len;
                while ((len = is.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, ps);
        }
    }
}

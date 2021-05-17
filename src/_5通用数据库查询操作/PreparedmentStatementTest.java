package _5通用数据库查询操作;

import Util.JdbcUtil;
import _2数据库的操作.User;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

/**
 * @author: Ansel
 * @date: 2021/5/4 0004 20:04
 * @description:
 */
public class PreparedmentStatementTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String user = scanner.nextLine();
        System.out.println("请输入密码:");
        String password = scanner.nextLine();

        String sql = "select user,password from user_table where user =? and password =?";
        User instance = getInstance(User.class, sql, user, password);
        if (instance != null) {
            System.out.println("登录成功");
        } else {
            System.out.println("用户名或密码错误!");
        }
    }

    public static <T> T getInstance(Class<T> tClass, String sql, Object... agrs) {
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

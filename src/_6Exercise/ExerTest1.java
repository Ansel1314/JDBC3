package _6Exercise;

import Util.JdbcUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

/**
 * @author: Ansel
 * @date: 2021/5/4 0004 20:43
 * @description:练习向数据库表中插入一条数据信息
 */
public class ExerTest1 {
    @Test
    public void test() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String user = scanner.nextLine();
        System.out.println("请输入邮箱:");
        String email = scanner.nextLine();
        System.out.println("请输入生日:");
        String birth = scanner.nextLine();

        String sql = "insert into customers(name,email,birth)values(?,?,?)";
        int update = update(sql, user, email, birth);
        if (update > 0) {
            System.out.println("添加成功");
        } else {
            System.out.println("添加失败");
        }
    }


    //通用的增删改操作
    public int update(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JdbcUtil.getConnection();
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
                System.out.println(args[i]);
            }
            /*
             * ps.execute();
             * 如果执行的是查询操作，有返回结果，则此方法返回ture
             * 如果执行的是增删改操作，则没有返回结果，此方法返回false
             *
             * */
            //ps.execute();
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, ps);
        }
        return 0;
    }
}

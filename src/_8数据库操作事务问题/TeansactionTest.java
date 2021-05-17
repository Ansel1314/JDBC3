package _8数据库操作事务问题;

import Util.JdbcUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author: Ansel
 * @date: 2021/5/6 0006 22:04
 * @description:
 */
public class TeansactionTest {
    /**
     * 1.什么叫数据库事务?
     * 事务:一组逻辑操作单元，是数据从一种状态换到另一种状态
     * >逻辑操作单元：一个或多个DML操作。
     * 2.事务处理的原则：保证所有事务都作为一个工作单元来执行，即使出现了故障，都不能改变这种执行方式
     * 当在一个事务中执行多个操作时，要么所有的事务都被提交(commit),那么这些修改就永久的保存
     * 下来：要么数据库管理系统将放弃所作的所有修改，整个事务回滚(rollback)到最初的状态
     * <p>
     * 3.数据一旦被提交，不可被回滚
     * <p>
     * 4.那些操作会导致数据被自动提交?
     * >DDL操作一旦被执行，都会被自动提交
     * >set autocommit =false 对DDL操作失效
     * >DML 默认情况下，一旦执行，就会自动提交.
     * >我们可以通过set autocommit =false的方式取消DML操作的自动提交
     * >默认在关闭连接时，会自动的提交数据
     */


    //考虑数据库事务的转账操作
    @Test
    public void TestUpDate() {
        Connection connection = null;
        try {
            connection = JdbcUtil.getConnection();
            //取消数据的自动提交
            connection.setAutoCommit(false);
            String sql1 = "update user_table set balance = balance-100 where user =?";
            update(connection, sql1, "AA");

            //模拟网络异常导致的转账失败
//            System.out.println(10/0);

            String sql2 = "update user_table set balance = balance+100 where user =?";
            update(connection, sql2, "BB");

            //提交数据
            connection.commit();
            System.out.println("转账成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, null);
        }

    }

    public int update(Connection connection, String sql, Object... args) {

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(null, ps);
        }
        return 0;
    }
}

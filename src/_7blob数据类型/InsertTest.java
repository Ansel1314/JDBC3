package _7blob数据类型;

import Util.JdbcUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author: Ansel
 * @date: 2021/5/5 0005 18:36
 * @description:使用PreparedStatement实现批量插入
 */
public class InsertTest {

    //插入方式一：
    @Test
    public void testInsert1() throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            connection = JdbcUtil.getConnection();
            String sql = "insert into goods(name)values(?)";
            ps = connection.prepareStatement(sql);
            for (int i = 1; i <= 20000; i++) {
                ps.setObject(1, "name_" + i);

                ps.execute();
            }
            long end = System.currentTimeMillis();
            System.out.println("插入所花费的时间:" + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, ps);
        }
    }

    //插入方式二：1.addBatch()、executeBatch、clearBatch();mysql默认不支持
    //2.Mysql默认是关闭批处理的，我们需要通过一个参数，让mysql开启对批处理的支持,?rewriteBatchedStatements=true写在配置文件URL的后面
    //3.使用更新的mysql驱动:mysql-connector-java-5.1.37-bin.jar
    //4.设置连接不允许自动提交数据
    //批量插入效率是这几种最高的
    @Test
    public void testInsert2() {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            connection = JdbcUtil.getConnection();

            //设置不允许自动提交数据
            connection.setAutoCommit(false);
            String sql = "insert into goods(name)values(?)";
            ps = connection.prepareStatement(sql);
            for (int i = 1; i <= 1000000; i++) {
                ps.setObject(1, "name_" + i);

                //1.攒sql
                ps.addBatch();
                if (i % 500 == 0) {
                    //2.执行batch
                    ps.executeBatch();

                    //3.清空Batch
                    ps.clearBatch();
                }
            }
            //提交数据
            connection.commit();
            long end = System.currentTimeMillis();
            System.out.println("插入所花费的时间:" + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(connection, ps);
        }
    }
}

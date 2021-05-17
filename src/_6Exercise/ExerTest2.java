package _6Exercise;

import Util.JdbcUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

/**
 * @author: Ansel
 * @date: 2021/5/4 0004 21:29
 * @description:
 */
public class ExerTest2 {
    //问题1:向examstudent中插入学生信息
    @Test
    public void TestInsert() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("四级/六级:");
        int type = scanner.nextInt();
        System.out.println("身份证号:");
        String IDCard = scanner.next();
        System.out.println("准考证号:");
        String examCard = scanner.next();
        System.out.println("学生姓名:");
        String studentname = scanner.next();
        System.out.println("所在城市:");
        String location = scanner.next();
        System.out.println("考试成绩:");
        int grade = scanner.nextInt();

        String sql = "insert into examstudent(type,IDCard,examCard,studentname,location,grade)values(?,?,?,?,?,?)";
        int update = update(sql, type, IDCard, examCard, studentname, location, grade);
        if (update > 0) {
            System.out.println("添加成功");
        } else
            System.out.println("添加失败");

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

    //问题2:根据身份证号或者准考证号查询学生成绩信息
    @Test
    public void QueryWithIDCardOrExamCard() {
        System.out.println("请选择您要输入的类型:");
        System.out.println("a.准考证号");
        System.out.println("b.身份证号");
        Scanner scanner = new Scanner(System.in);
        String selection = scanner.next();
        if ("a".equalsIgnoreCase(selection)) {
            System.out.println("请输入准考证号:");
            String examCard = scanner.next();
            String sql = "select FlowID flowID,Type type,IDCard,ExamCard examCard,StudentName studentname,Location location,Grade grade from examstudent where examCard =?";
            Student instance = getInstance(Student.class, sql, examCard);
            if (instance != null) {
                System.out.println(instance);
            } else
                System.out.println("您输入的准考证号有误");

        } else if ("b".equalsIgnoreCase(selection)) {
            System.out.println("请输入身份证号:");
            String IDCard = scanner.next();
            String sql = "select FlowID flowID,Type type,IDCard,ExamCard examCard,StudentName studentname,Location location,Grade grade from examstudent where IDCard =?";
            Student instance = getInstance(Student.class, sql, IDCard);
            if (instance != null) {
                System.out.println(instance);
            } else
                System.out.println("您输入的身份证号有误");

        } else {
            System.out.println("输入错误!,请重新进入程序");
        }


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


    //问题3:删除指定的学生信息
    @Test
    public void testDeleteByExamCard() {
        System.out.println("请输入学生的考号:");
        Scanner scanner = new Scanner(System.in);
        String examCard = scanner.next();
        String sql = "delete from examstudent where examCard =?";
        int delete = update(sql, examCard);
        if (delete > 0) {
            System.out.println("删除成功");
        } else
            System.out.println("查无此人，请重新输入");

    }
}

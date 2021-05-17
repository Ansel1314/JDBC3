package _10BaseDAO优化升级;

import Util.JdbcUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Ansel
 * @date: 2021/5/8 0008 10:15
 * @description:封装了针对数据库表的通用操作
 */
public abstract class BaseDAO<T> {
    private Class<T> tClass;

    {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType type = (ParameterizedType) genericSuperclass;
        Type[] typeArguments = type.getActualTypeArguments();
        tClass = (Class<T>) typeArguments[0];
    }

    //通用的增删改操作(考虑上事务)
    public int update(Connection connection, String sql, Object... args) {
        PreparedStatement ps = null;
        try {

            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
                System.out.println(args[i]);
            }

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(null, ps);
        }
        return 0;
    }

    //针对于不同的表查询的通用方法，返回表中的一条记录(考虑上事务)
    public T getInstance(Connection connection, String sql, Object... agrs) {

        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {

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
            JdbcUtil.closeResource(null, ps, resultSet);
        }
        return null;
    }

    //针对于不同的表查询的通用方法，返回表中的多条记录(考虑上事务)
    public List<T> getForQueryList(Connection connection, String sql, Object... agrs) {

        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {

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
            JdbcUtil.closeResource(null, ps, resultSet);
        }
        return null;
    }

    public <E> E getValue(Connection connection, String sql, Object... agrs) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < agrs.length; i++) {
                ps.setObject(i + 1, agrs[i]);
            }

            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return (E) resultSet.getObject(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.closeResource(null, ps, resultSet);
        }

        return null;
    }

}

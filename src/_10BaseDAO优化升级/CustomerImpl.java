package _10BaseDAO优化升级;

import _3数据库操作PrepareStatement.Customers;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author: Ansel
 * @date: 2021/5/8 0008 20:27
 * @description:实现CustomerDAO接口规范
 */
public class CustomerImpl extends BaseDAO<Customers> implements CustomerDAO {
    @Override
    public void insert(Connection connection, Customers customers) {
        //将customers添加到数据库中
        String sql = "insert into customers(name,email,birth)values(?,?,?)";
        update(connection, sql, customers.getName(), customers.getEmail(), customers.getBrith());

    }

    @Override
    public int deleteById(Connection connection, int id) {
        //针对指定ID删除表中的一条记录
        String sql = "delete from customers where id =?";
        return update(connection, sql, id);

    }

    @Override
    public void update(Connection connection, Customers customers) {
        //针对内存中的customers，修改数据表中指定记录
        String sql = "update customers set name=?,email=?,birth=? where id=?";
        update(connection, sql, customers.getName(), customers.getEmail(), customers.getBrith(), customers.getId());
    }

    @Override
    public Customers getCustomerById(Connection connection, int id) {
        //针对指定的ID得到对应的customers对象
        String sql = "select id,name,email,birth from customers where id=?";
        Customers instance = getInstance(connection, sql, id);
        return instance;
    }

    @Override
    public List<Customers> getAll(Connection connection) {
        //返回数据中的所有数据构成的集合
        String sql = "select id,name,email,birth from customers";
        List<Customers> forQueryList = getForQueryList(connection, sql);

        return forQueryList;
    }

    @Override
    public Long getCount(Connection connection) {
        //返回数据库中数据的条目数
        String sql = "select count(*) from customers";
        return getValue(connection, sql);
    }

    @Override
    public Date getMaxBirth(Connection connection) {
        //返回数据库中最大生日
        String sql = "select max(birth) from customers";
        return getValue(connection, sql);
    }
}

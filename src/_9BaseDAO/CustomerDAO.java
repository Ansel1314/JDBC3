package _9BaseDAO;

import _3数据库操作PrepareStatement.Customers;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author: Ansel
 * @date: 2021/5/8 0008 20:27
 * @description:此接口用于规范针对于customers表的常用操作
 */
public interface CustomerDAO {
    //将customers添加到数据库中
    void insert(Connection connection, Customers customers);

    //针对指定ID删除表中的一条记录
    int deleteById(Connection connection, int id);

    //针对内存中的customers，修改数据表中指定记录
    void update(Connection connection, Customers customers);

    //针对指定的ID得到对应的customers对象
    Customers getCustomerById(Connection connection, int id);

    //返回数据中的所有数据构成的集合
    List<Customers> getAll(Connection connection);

    //返回数据库中数据的条目数
    Long getCount(Connection connection);

    //返回数据库中最大生日
    Date getMaxBirth(Connection connection);
}

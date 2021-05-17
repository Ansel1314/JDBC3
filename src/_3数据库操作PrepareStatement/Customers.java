package _3数据库操作PrepareStatement;

import java.sql.Date;

/**
 * @author: Ansel
 * @date: 2021/5/2 20:25
 * @description:
 */
public class Customers {
    private int id;
    private String name;
    private String email;
    private Date birth;

    public Customers() {
    }

    public Customers(int id, String name, String email, Date birth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBrith() {
        return birth;
    }

    public void setBrith(Date brith) {
        this.birth = brith;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", brith=" + birth +
                '}';
    }
}

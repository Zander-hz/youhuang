package cn.youhuang.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author iaoao
 * @date 2020/3/3 18:08
 * @description
 */
@Table(name = "user")
public class User implements Serializable {
    @Id //在主键上标明注解
    /*
    * GenerationType.IDENTITY  mysql数据库，主键自增
    * GenerationType.SEQUENCE   Oracle数据库，序列化
    * GenerationType.AUTO   系统自动配置主键
    * GenerationType.TABLE  从表中取值，设置主键
    * */
    @GeneratedValue(strategy = GenerationType.IDENTITY) //设置主键自增
    private Integer id;
    private String username;
    private String password;
    private String birthday;

//    //表示用户关联的订单
//    private List<Order> orderList = new ArrayList<>();
//
//    //表示用户关联的角色
//    private List<Role> roleList = new ArrayList<>();
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", roleList=" + roleList +
//                '}';
//    }
//
//    public List<Order> getOrderList() {
//        return orderList;
//    }
//
//    public void setOrderList(List<Order> orderList) {
//        this.orderList = orderList;
//    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}

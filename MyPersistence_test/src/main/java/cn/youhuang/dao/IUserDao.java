package cn.youhuang.dao;

import cn.youhuang.pojo.User;
import org.dom4j.DocumentException;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author iaoao
 * @date 2020/3/2 11:38
 * @description
 */
public interface IUserDao {
    //查询所有用户
    public List<User> findAll() throws Exception;

    //根据条件进行用户查询
   public User findByCondition(User user) throws Exception;

   public int insertUser(User user);

   public int updateUserById(User user);

   public int deleteUserById(User user);
}

package cn.youhuang.dao;

import cn.youhuang.io.Resources;
import cn.youhuang.pojo.User;
import cn.youhuang.sqlSession.SqlSession;
import cn.youhuang.sqlSession.SqlSessionFactory;
import cn.youhuang.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author iaoao
 * @date 2020/3/2 11:39
 * @description
 */
public class UserDaoImpl {

    public List<User> findAll() throws Exception{
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

             List<User> users = sqlSession.selectList("user.selectList");
        for (User user : users) {
            System.out.println(user);
        }
        return users;
    }


    public User findByCondition(User user) throws Exception{
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用

        User user_test = sqlSession.selectOne("user.selectOne",user);
        System.out.println(user_test);


        return null;
    }
}

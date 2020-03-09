package cn.youhuang.test;

import cn.youhuang.dao.IUserDao;
import cn.youhuang.io.Resources;
import cn.youhuang.pojo.User;
import cn.youhuang.sqlSession.SqlSession;
import cn.youhuang.sqlSession.SqlSessionFactory;
import cn.youhuang.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.List;

/**
 * @author iaoao
 * @date 2020/2/28 12:08
 * @description
 */
public class MyPersistenceTest {

    private SqlSession sqlSession;

    @Before
    public void init() throws PropertyVetoException, DocumentException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = sqlSessionFactory.openSession();
    }
    @Test
    public void testSelect() throws Exception{

        //调用
        User user_test = new User();
        user_test.setId(1);
        user_test.setUsername("Tom");
      /*  User user = sqlSession.selectOne("user.selectOne",user_test);
        System.out.println(user);*/

//        List<User> users = sqlSession.selectList("user.selectList");
//        for (User user : users) {
//            System.out.println(user);
//        }

        //创建代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        User userByCondition = userDao.findByCondition(user_test);
        System.out.println("userByCondition:id=1"+userByCondition);

        List<User> users = userDao.findAll();
        for (User user : users) {
           System.out.println(user);
        }
    }

    @Test
    public void testInsert(){
        User user = new User();
        user.setId(2);
        user.setUsername("Bob");
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        userDao.insertUser(user);
    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(1);
        user.setUsername("Jam");
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        userDao.updateUserById(user);
    }

    @Test
    public void testDelete(){
        User user = new User();
        user.setId(2);
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        userDao.deleteUserById(user);
    }
}

package cn.youhuang.test;

import cn.youhuang.dao.IUserDao;
import cn.youhuang.dao.UserDaoImpl;
import cn.youhuang.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author iaoao
 * @date 2020/3/2 21:33
 * @description
 */
public class MybatisTest {

    private SqlSession sqlSession;
    @Before
    public void init() throws IOException {
        //1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //2.解析配置文件，并创建sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //3.生成sqlSession
        //默认开始一个事务，但该事务不会自动提交，在进行增删改操作时，要手动提交事务
        sqlSession = sqlSessionFactory.openSession();
        //可设置事务自动提交
//        sqlSession = sqlSessionFactory.openSession(true);

    }

    @Test
    public void testSelect() throws IOException {
        //4.sqlSession调用方法:statementId,(...params)
        List<User> users = sqlSession.selectList("cn.youhuang.dao.IUserDao.findAll");

        for (User user : users) {
            System.out.println(user);
        }

    }

    @Test
    public void testInsert() throws IOException {

        User user = new User();
        user.setId(4);
        user.setUsername("AAA");
        sqlSession.insert("cn.youhuang.dao.IUserDao.saveUser",user);

        sqlSession.commit();
    }

    @Test
    public void testUpdate() throws IOException {

        User user = new User();
        user.setId(4);
        user.setUsername("Lin");
        sqlSession.insert("cn.youhuang.dao.IUserDao.updateUser",user);

        sqlSession.commit();
    }

    @Test
    public void testDelete() throws IOException {

        User user = new User();
        user.setId(4);
        sqlSession.insert("cn.youhuang.dao.IUserDao.deleteUser",user);

        sqlSession.commit();
    }

    @Test
    public void testFindAllTradition() throws IOException {
        IUserDao userDao = new UserDaoImpl();
        List<User> all = userDao.findAll();
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void testFindAllProxy() throws IOException {
        //创建代理对象
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        List<User> all = mapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void testFindByCondition() throws IOException {
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);

        User user = new User();
//        user.setId(3);
        user.setUsername("tom");

        List<User> all = mapper.findByCondition(user);
        for (User users : all) {
            System.out.println(users);
        }
    }

    @Test
    public void testFindByIds() throws IOException {
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);

        int[] arr = {1,2};

        List<User> all = mapper.findByIds(arr);
        for (User users : all) {
            System.out.println(users);
        }
    }

    @After
    public void destroy(){
        sqlSession.close();
    }
}

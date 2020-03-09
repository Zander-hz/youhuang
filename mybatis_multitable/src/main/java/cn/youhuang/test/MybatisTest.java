package cn.youhuang.test;

import cn.youhuang.mapper.IOrderMapper;
import cn.youhuang.mapper.IUserMapper;
import cn.youhuang.mapper.UserMapper;
import cn.youhuang.pojo.Order;
import cn.youhuang.pojo.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author iaoao
 * @date 2020/3/3 18:45
 * @description
 */
public class MybatisTest {

    private SqlSession sqlSession;
    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void init() throws IOException {
        //1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //2.解析配置文件，并创建sqlSessionFactory工厂对象
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //3.生成sqlSession
        //默认开始一个事务，但该事务不会自动提交，在进行增删改操作时，要手动提交事务
        sqlSession = sqlSessionFactory.openSession(true);
    }

    @Test
    public void testSelectOrder() throws IOException {
        IOrderMapper mapper = sqlSession.getMapper(IOrderMapper.class);
        List<Order> orderAndUser = mapper.findOrderAndUser();

        for (Order order : orderAndUser) {
            System.out.println(order);
        }

    }

    @Test
    public void testSelectUser() throws IOException {
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        List<User> all = mapper.findAll();

        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void testSelectRole() throws IOException {
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        List<User> all = mapper.findAllUserAndRole();

        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void testAddUser() throws IOException {
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        User user = new User();
        user.setUsername("Zander");

        mapper.addUser(user);

    }

    @Test
    public void testUpdateUser() throws IOException {
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        User user = new User();
        user.setId(3);
        user.setUsername("Zander1");

        mapper.updateUser(user);
    }

    @Test
    public void testSelectAnnotationsUser() throws IOException {
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        List<User> all = mapper.selectUser();

        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void testDeleteUser() throws IOException {
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        mapper.deleteUser(3);
        testSelectAnnotationsUser();
    }

    @Test
    public void testOneToOne() throws IOException {
        IOrderMapper mapper = sqlSession.getMapper(IOrderMapper.class);
        List<Order> orderAndUser = mapper.findOrderAndUser();
        for (Order order : orderAndUser) {
            System.out.println(order);
        }
    }

    @Test
    public void testOneToMany() throws IOException {
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        List<User> all = mapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void testManyToMany() throws IOException {
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        List<User> all = mapper.findAllUserAndRole();
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void testRedisCache() throws IOException {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();

        IUserMapper mapper1 = sqlSession1.getMapper(IUserMapper.class);
        IUserMapper mapper2 = sqlSession2.getMapper(IUserMapper.class);
        IUserMapper mapper3 = sqlSession3.getMapper(IUserMapper.class);

        User user1 = mapper1.findUserById(3);
        sqlSession1.close();//清空一级缓存


        User user2 = mapper2.findUserById(3);

        //二级缓存存储的是数据内容，故地址不一样
        System.out.println(user1 == user2);
    }


    @Test
    public void testPageHelper() throws IOException {
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        PageHelper.startPage(1,1);
        List<User> all = mapper.selectUser();
        for (User user : all) {
            System.out.println(user);
        }

        PageInfo<User> userPageInfo = new PageInfo<>(all);
        System.out.println("总条数为："+userPageInfo.getTotal());
        System.out.println("总页数为："+userPageInfo.getPages());
        System.out.println("当前页为："+userPageInfo.getPageNum());
        System.out.println("每页显示条数为："+userPageInfo.getPageSize());


    }

    @Test
    public void testCommonMapper() throws IOException {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
//        user.setId(1);
//        User selectOne = mapper.selectOne(user);
//        System.out.println(selectOne);
        List<User> select = mapper.select(user);
        for (User user1 : select) {
            System.out.println(user1);
        }

        //2.example方法
        Example example = new Example(User.class);
        //查询id为1的user表
        example.createCriteria().andEqualTo("id",1);
        List<User> users = mapper.selectByExample(example);
        for (User user1 : users) {
            System.out.println("example方式:"+user);
        }
    }


    @After
    public void destroy(){
        sqlSession.close();
    }
}

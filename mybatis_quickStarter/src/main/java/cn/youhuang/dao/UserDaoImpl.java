package cn.youhuang.dao;

import cn.youhuang.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author iaoao
 * @date 2020/3/3 9:43
 * @description 传统开发方式，需手动编写实现类
 */
public class UserDaoImpl implements IUserDao{

    @Override
    public List<User> findAll() throws IOException {
        //1.Resources工具类，配置文件的加载，吧配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //2.解析配置文件，并创建sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //3.生成sqlSession
        //默认开始一个事务，但该事务不会自动提交，在进行增删改操作时，要手动提交事务
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //4.sqlSession调用方法:statementId,(...params)
        List<User> users = sqlSession.selectList("cn.youhuang.dao.IUserDao.findAll");

        sqlSession.close();
        return users;
    }

    @Override
    public List<User> findByCondition(User user) {
        return null;
    }

    @Override
    public List<User> findByIds(int[] ids) {
        return null;
    }
}

package cn.youhuang.sqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author iaoao
 * @date 2020/2/29 13:28
 * @description
 */
public interface SqlSession {
    //为Dao接口生成代理实现类
    public <T> T getMapper(Class<?> mapperClass);
    //查询所有,可能存在根据模糊条件查询多个，故传递的参数还有可变参
    public <E> List<E> selectList(String statementId,Object... params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IntrospectionException, InvocationTargetException, InstantiationException;
    //根据条件查询单个
    public <T> T selectOne(String statementId,Object... params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IntrospectionException, InvocationTargetException, InstantiationException;

    public int insert(String statement, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException;

    public int update(String statement, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException;

    public int delete(String statement, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException;
}

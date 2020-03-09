package cn.youhuang.sqlSession;

import cn.youhuang.pojo.Configuration;
import cn.youhuang.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author iaoao
 * @date 2020/3/1 17:18
 * @description
 */
public interface Executor {

    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement,Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException;

    public int update(Configuration configuration,MappedStatement ms, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException;
}

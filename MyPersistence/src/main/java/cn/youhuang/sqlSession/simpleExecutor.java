package cn.youhuang.sqlSession;

import cn.youhuang.config.BoundSql;
import cn.youhuang.pojo.Configuration;
import cn.youhuang.pojo.MappedStatement;
import cn.youhuang.utils.GenericTokenParser;
import cn.youhuang.utils.ParameterMapping;
import cn.youhuang.utils.ParameterMappingTokenHandler;
import cn.youhuang.utils.TokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author iaoao
 * @date 2020/3/1 17:20
 * @description
 */
public class simpleExecutor implements Executor {
    //执行查询的具体操作
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException {
        //编写JDBC
        //1.注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        //2.1获取sql语句:select * from user where id = #{id} and username = #{username}
        String sql = mappedStatement.getSql();
        //2.2转换sql语句:select * from user where id = ? and username = ?
        //转换的过程中需要对#{}中的值进行解析存储
        BoundSql boundSql = getBoundSql(sql);

        //3.获取预处理对象:preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        //4.设置参数
        //获取参数类的全路径，并获取其class
        String parameterType = mappedStatement.getParameterType();
        Class<?> parameterTypeClass = getClassType(parameterType);

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();

            //反射
            //根据content的值，获取实体对象参数中的属性值
            Field declaredField = parameterTypeClass.getDeclaredField(content);
            //若content属性为私有的，则通过此设置，暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);

            //参数设置到sql语句中
            preparedStatement.setObject(i+1,o);
        }
        //5.执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        //6.封装返回结果集
        //6.1获取返回类型的全路径，及其class
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);

        //6.2将结果封装到结果集中
        ArrayList<Object> resultList = new ArrayList<>();
        while (resultSet.next()){
            //6.3获取结果集类的实现类
            Object o = resultTypeClass.newInstance();
            //获取元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //列的下标从1开始，所以此处i起始位1值；i要小于等于元数据的列数
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //字段名
                String columnName = metaData.getColumnName(i);
                //获取字段值
                Object value = resultSet.getObject(columnName);

                //通过反射或内省，根据数据库表和实体的对应关系，完成封装
                //对resultTypeClass类中的columnName属性生成读写方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,value);
            }
            //6.4将封装好的一个个结果集对象，封装到list集合中
            resultList.add(o);
        }
        return (List<E>) resultList;
    }

    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        //编写JDBC
        //1.注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        //2.1获取sql语句:select * from user where id = #{id} and username = #{username}
        String sql = mappedStatement.getSql();
        //2.2转换sql语句:select * from user where id = ? and username = ?
        //转换的过程中需要对#{}中的值进行解析存储
        BoundSql boundSql = getBoundSql(sql);

        //3.获取预处理对象:preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        //4.设置参数
        //获取参数类的全路径，并获取其class
        String parameterType = mappedStatement.getParameterType();
        Class<?> parameterTypeClass = getClassType(parameterType);

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();

            //反射
            //根据content的值，获取实体对象参数中的属性值
            Field declaredField = parameterTypeClass.getDeclaredField(content);
            //若content属性为私有的，则通过此设置，暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);

            //参数设置到sql语句中
            preparedStatement.setObject(i+1,o);
        }
        int rows = preparedStatement.executeUpdate();
        return rows;
    }


    /**
     * 根据类的全路径，获取类的class对象
     * */
    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (parameterType != null){
            Class<?> aClass = Class.forName(parameterType);
            return aClass;
        }
        return null;
    }

    /**
     * 完成对sql语句中的#{}解析工作
     * 1.将#{}使用？进行代替
     * 2.解析初#{}里面的值进行存储
     * */
    private BoundSql getBoundSql(String sql) {
        //标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析后的sql:select * from user where id = ? and username = ?
        String parseSql = genericTokenParser.parse(sql);
        //获取#{}中解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(parseSql,parameterMappings);
        return boundSql;
    }
}

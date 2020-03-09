package cn.youhuang.sqlSession;

import cn.youhuang.pojo.Configuration;
import cn.youhuang.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author iaoao
 * @date 2020/2/29 13:29
 * @description
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用JDK动态代理，为Dao接口生成代理对象，并返回
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //底层还是执行JDBC代码，根据不同情况，来调用selectList或者selectOne
                /*1.准备参数:statementId:sql语句的唯一表示:namespace.id
                而invoke方法中无法获取映射文件中namespace和id的值，
                故规范namespace要写Dao层对应对象的全路径，id要和方法名一致
                namespace.id = 接口全限定名.方法名
                2.准备参数：params。params即为传过来的参数Object[] args
                */
                //方法名
                String methodName = method.getName();
                //接口的权限定名,通过方法获取字节码对象，获取类名
                String className = method.getDeclaringClass().getName();

                String statementId = className + "." + methodName;

                //获取被调用方法的返回值类型
                Type genericReturnType = method.getGenericReturnType();
                //判断是否进行了泛型类型参数化，即判断返回值类型是否有泛型：有泛型即为集合形式，没有泛型即为实体对象
                if (genericReturnType instanceof ParameterizedType){
                    List<Object> objects = selectList(statementId, args);
                    return objects;
                }
                if(methodName.startsWith("find")) {
                    //若不是泛型，则直接返回实体对象
                    return selectOne(statementId, args);
                } else if (methodName.startsWith("insert")) {
                    return  insert(statementId, args);
                }else if (methodName.startsWith("delete")){
                    return delete(statementId, args);
                }else {
                    return update(statementId, args);
                }
            }
        });

        return (T) proxyInstance;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IntrospectionException, InvocationTargetException, InstantiationException {
        //完成对simpleExecutor里的query方法的调用
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.query(configuration, mappedStatement, params);

        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IntrospectionException, InvocationTargetException, InstantiationException {
        List<Object> objects = selectList(statementId, params);
        if(objects.size()==1){
            return (T) objects.get(0);
        }else if(objects.size() > 1){
            throw new RuntimeException("查询结果过多");
        }else {
            throw new RuntimeException("查询结果为空");
        }
    }

    @Override
    public int insert(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        return doUpdate(statementId, params);
    }

    @Override
    public int update(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        return doUpdate(statementId, params);
    }

    @Override
    public int delete(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        return doUpdate(statementId, params);
    }

    public int doUpdate(String statementId, Object... params) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        simpleExecutor simpleExecutor = new simpleExecutor();
        int rows = simpleExecutor.update(configuration, mappedStatement, params);
        return rows;
    }
}

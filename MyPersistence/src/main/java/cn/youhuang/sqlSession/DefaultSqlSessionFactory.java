package cn.youhuang.sqlSession;

import cn.youhuang.pojo.Configuration;

/**
 * @author iaoao
 * @date 2020/2/29 13:23
 * @description
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}

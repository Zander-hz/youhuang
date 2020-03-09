package cn.youhuang.sqlSession;

/**
 * @author iaoao
 * @date 2020/2/28 22:24
 * @description
 */
public interface SqlSessionFactory {

    public SqlSession openSession();
}

package cn.youhuang.sqlSession;

import cn.youhuang.config.XMLConfigBuilder;
import cn.youhuang.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author iaoao
 * @date 2020/2/28 22:22
 * @description
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) throws DocumentException, PropertyVetoException {
        //1.使用dom4j解析配置文件，将解析出来的内容封装到Configuration
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);

        //2.创建SqlSessionFactory对象
        /**
         * SqlSessionFactory对象为工厂类，生产sqlSession对象
         * sqlSession对象为会话对象
         * */
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return defaultSqlSessionFactory;
    }
}

package cn.youhuang.config;

import cn.youhuang.io.Resources;
import cn.youhuang.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author iaoao
 * @date 2020/2/28 22:26
 * @description
 */
public class XMLConfigBuilder {
    //将此成员变量写在外面，当构建此对象时，通过就会调用下方的无参构造方法，即会为此成员变量进行赋值
    private  Configuration configuration;
    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }
    /**该方法是使用dom4j将配置文件进行解析，封装configuration*/
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(inputStream);
        //获取配置文件的根对象，即salMapConfig中的<configuration>
        Element rootElement = document.getRootElement();
        //使用xPath表达式，双斜杆"//"表示无论元素在何位置，都可被取到
        List<Element> list = rootElement.selectNodes("//property");
        //构建容器，存储解析出来的元素
        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }
        //创建连接池对象
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        //将数据库配置信息存储到configuration对象中
        configuration.setDataSource(comboPooledDataSource);


        //mapper.xml解析：获取路径--字节输入流--dom4j进行解析
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            //获取路径
            String mapperPath = element.attributeValue("resource");
            //将文件加载为字节输入流
            InputStream resourceAsStream = Resources.getResourceAsStream(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            //传递参数，进行dom4j解析
            xmlMapperBuilder.parse(resourceAsStream);
        }

        return configuration;
    }
}

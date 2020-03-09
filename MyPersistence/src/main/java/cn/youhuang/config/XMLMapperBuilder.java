package cn.youhuang.config;

import cn.youhuang.pojo.Configuration;
import cn.youhuang.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author iaoao
 * @date 2020/2/29 12:56
 * @description
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public  void parse(InputStream inputStream) throws DocumentException {

        Document document = new SAXReader().read(inputStream);
        //获得根标签
        Element rootElement = document.getRootElement();
        //获取根标签的namespace属性值
        String namespace = rootElement.attributeValue("namespace");

        List<Element> list = rootElement.selectNodes("//select|//insert|//update|//delete");
        for (Element element : list) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            //获得元素标签内内容，即sql语句，并去除两端空格
            String sqlText = element.getTextTrim();
            //将解析到的数据封装为MappedStatement
            //将解析到的数据封装为MappedStatement
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sqlText);
            //创建存储的key值，statementId
            String key = namespace + "." + id;
            //封装为Configuration对象
            configuration.getMappedStatementMap().put(key,mappedStatement);
        }
    }
}

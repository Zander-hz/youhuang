package cn.youhuang.pojo;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author iaoao
 * @date 2020/2/28 16:09
 * @description 此类封装sql数据库的配置信息，以及sql语句的解析信息
 */
public class Configuration {

    private DataSource dataSource;
    /**
     * key:statmentId
     * value:封装好的mapperStatement对象
     * */
    Map<String,MappedStatement> mappedStatementMap = new HashMap<String, MappedStatement>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}

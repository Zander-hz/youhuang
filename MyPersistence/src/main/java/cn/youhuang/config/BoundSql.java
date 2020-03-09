package cn.youhuang.config;

import cn.youhuang.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iaoao
 * @date 2020/3/1 17:56
 * @description
 */
public class BoundSql {
    //解析后的sql
    private  String sqlText;

    //#{}解析后的参数
    private List<ParameterMapping> parameterMappingList = new ArrayList<>();

    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}

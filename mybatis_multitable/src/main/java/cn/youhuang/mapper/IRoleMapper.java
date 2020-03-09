package cn.youhuang.mapper;

import cn.youhuang.pojo.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author iaoao
 * @date 2020/3/4 16:14
 * @description
 */
public interface IRoleMapper {

    @Select("select * from sys_role r, sys_user_role ur where r.id = ur.roleid and ur.userid = #{uid}")
    public List<Role> findRoleByUid(Integer uid);

}

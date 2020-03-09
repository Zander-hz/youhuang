package cn.youhuang.mapper;

import cn.youhuang.pojo.User;
import org.apache.ibatis.annotations.*;
import org.mybatis.caches.redis.RedisCache;

import java.util.List;

/**
 * @author iaoao
 * @date 2020/3/3 21:32
 * @description
 */
//通过implementation属性，配置开启的二级缓存的具体实现类，此处为开启redis缓存
@CacheNamespace(implementation = RedisCache.class) //注解方式的sql注入，开启二级缓存
public interface IUserMapper {
    //查询所有用户，同时查询每个用户关联的订单信息
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "orderList",column = "id",javaType = List.class,many = @Many(select = "cn.youhuang.mapper.IOrderMapper.findOrderByUid")),

    })
    @Select("select * from user")
    public List<User> findAll();

    //查询所有用户，同时查询每个用户关联的角色信息
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "roleList",column = "id",javaType = List.class,many = @Many(select = "cn.youhuang.mapper.IRoleMapper.findRoleByUid"))
    })
    @Select("select * from user")
    public List<User> findAllUserAndRole();

    //添加用户
    @Insert("insert into user(username) values(#{username})")
    public void addUser(User user);

    //更新用户
    @Update("update user set username = #{username} where id = #{id}")
    public void updateUser(User user);

    //查询用户
    @Select("select * from user")
    public List<User> selectUser();

    //删除用户
    @Delete("delete from user where id = #{id}")
    public void deleteUser(Integer id);

    //根据id查询用户
    @Select("select * from user where id = #{id}")
    public User findUserById(Integer id);
}

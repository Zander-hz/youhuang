package cn.youhuang.mapper;

import cn.youhuang.pojo.Order;
import cn.youhuang.pojo.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author iaoao
 * @date 2020/3/3 18:16
 * @description
 */
public interface IOrderMapper {
    //查询订单的同时，还查询该订单所属的用户
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "total",column = "total"),
            @Result(property = "user",column = "uid",javaType = User.class,one = @One(select = "cn.youhuang.mapper.IUserMapper.findUserById")),

    })
    @Select("select * from orders")
    public List<Order> findOrderAndUser();

    @Select("select * from orders where uid = #{uid}")
    public List<Order> findOrderByUid(Integer uid);
}

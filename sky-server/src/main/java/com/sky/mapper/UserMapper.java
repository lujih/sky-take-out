package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {

    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    void addUser(User newUser);

    @Select("select * from user where id = #{userid}")
    User getById(Long userId);

    /**
     * 用户统计
     * @param beginTime
     * @param endTime
     * @return
     */
    @Select("select count(id) from  user where create_time between #{beginTime} and #{endTime}")
    Integer getUserByTime(LocalDateTime beginTime, LocalDateTime endTime);

    @Select("select count(id) from user where create_time < #{endTime}")
    Integer getallUserByTime(LocalDateTime endTime);
}

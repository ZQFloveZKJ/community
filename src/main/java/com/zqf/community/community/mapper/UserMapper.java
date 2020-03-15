package com.zqf.community.community.mapper;

import com.zqf.community.community.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_greate,gmt_modified,avatar_url) values(#{name},#{account_id},#{token},#{gmt_greate},#{gmt_modified},#{avatar_url})")
    void insert(User user);
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
}

package org.speaker.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.speaker.pojo.entity.User;

@Mapper
public interface UserMapper {

    void insert(User user);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User getById(Long id);

    @Select("SELECT * FROM user where username = #{username}")
    User getByUsername(String username);

    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{encryptPwd}")
    User getByUsernameAndPassword(String username, String encryptPwd);
}

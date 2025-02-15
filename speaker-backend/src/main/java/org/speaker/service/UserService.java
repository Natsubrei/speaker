package org.speaker.service;

import jakarta.servlet.http.HttpServletRequest;
import org.speaker.pojo.entity.User;

public interface UserService {

    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param pwdCheck 校验密码
     * @return 返回注册成功账户的id，注册失败返回null
     */
    Long register(String username, String password, String pwdCheck);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param request http请求
     * @return 登录脱敏后的用户信息
     */
    User login(String username, String password, HttpServletRequest request);

    /**
     * 根据id查询用户
     * @param id 用户id
     * @return 用户信息
     */
    User getById(Long id);
}

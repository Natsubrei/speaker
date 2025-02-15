package org.speaker.service.impl;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.speaker.constant.MessageConstant;
import org.speaker.constant.UserConstant;
import org.speaker.exception.LoginFailedException;
import org.speaker.exception.RegisterFailedException;
import org.speaker.mapper.UserMapper;
import org.speaker.pojo.entity.User;
import org.speaker.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param pwdCheck 校验密码
     * @return 返回注册成功账户的id，注册失败返回null
     */
    @Override
    public Long register(String username, String password, String pwdCheck) {
        // 任意一个字符串为空，返回null
        if (StringUtils.isAnyBlank(username, password, pwdCheck)) {
            throw new RegisterFailedException(MessageConstant.USERNAME_OR_PASSWORD_NOT_INPUT);
        }
        // 检查用户名格式
        if (!username.matches(UserConstant.USERNAME_PATTERN)) {
            throw new RegisterFailedException(MessageConstant.USERNAME_OR_PASSWORD_FORMAT_ERROR);
        }
        // 检查密码与校验密码是否一致
        if (!password.equals(pwdCheck)) {
            throw new RegisterFailedException(MessageConstant.PASSWORDS_NOT_CONSISTENT);
        }
        // 检查密码格式
        if (!password.matches(UserConstant.USER_PWD_PATTERN)) {
            throw new RegisterFailedException(MessageConstant.USERNAME_OR_PASSWORD_FORMAT_ERROR);
        }
        // 查询是否已经存在该用户
        User user = userMapper.getByUsername(username);
        // 用户名已被占用
        if (user != null) {
            throw new RegisterFailedException(MessageConstant.USERNAME_EXIST);
        }
        // 密码加密
        String encryptPwd = DigestUtils.md5DigestAsHex((password + username).getBytes(StandardCharsets.UTF_8));
        // 用户名未被占用，创建新用户
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encryptPwd);
        // 新用户数据插入数据库
        userMapper.insert(newUser);
        // 返回新用户id
        return newUser.getId();
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param request http请求
     * @return 登录脱敏后的用户信息
     */
    @Override
    public User login(String username, String password, HttpServletRequest request) {
        // 任意一个字符串为空，返回null
        if (StringUtils.isAnyBlank(username, password)) {
            throw new LoginFailedException(MessageConstant.USERNAME_OR_PASSWORD_NOT_INPUT);
        }
        // 检查用户名格式
        if (!username.matches(UserConstant.USERNAME_PATTERN)) {
            throw new LoginFailedException(MessageConstant.USERNAME_OR_PASSWORD_FORMAT_ERROR);
        }
        // 检查密码格式
        if (!password.matches(UserConstant.USER_PWD_PATTERN)) {
            throw new LoginFailedException(MessageConstant.USERNAME_OR_PASSWORD_FORMAT_ERROR);
        }
        // 查询用户
        String encryptPwd = DigestUtils.md5DigestAsHex((password + username).getBytes(StandardCharsets.UTF_8));
        User user = userMapper.getByUsernameAndPassword(username, encryptPwd);
        // 若没找到符合条件的用户，说明用户名或密码错误
        if (user == null) {
            throw new LoginFailedException(MessageConstant.USERNAME_OR_PASSWORD_ERROR);
        }
        // 脱敏
        user.setPassword(null);
        // 设置session
        request.getSession().setAttribute(UserConstant.USER_INFO, user);
        return user;
    }

    /**
     * 根据id查询用户
     * @param id 用户id
     * @return 用户信息
     */
    @Override
    public User getById(Long id) {
        if (id == null) {
            return null;
        }
        return userMapper.getById(id);
    }
}

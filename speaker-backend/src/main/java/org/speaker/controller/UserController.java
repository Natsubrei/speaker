package org.speaker.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.speaker.result.Result;
import org.speaker.constant.MessageConstant;
import org.speaker.constant.UserConstant;
import org.speaker.pojo.dto.UserLoginDTO;
import org.speaker.pojo.dto.UserRegisterDTO;
import org.speaker.pojo.entity.User;
import org.speaker.service.UserService;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterDTO 用户注册数据传输对象
     * @return 用户id
     */
    @PostMapping("/register")
    public Result<Long> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO == null) {
            return Result.error();
        }
        log.info("用户注册：{}", userRegisterDTO);
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();
        String pwdCheck = userRegisterDTO.getPwdCheck();
        // 检查传入的用户名、密码以及确认密码是否为空
        if (StringUtils.isAnyBlank(username, password, pwdCheck)) {
            return Result.error();
        }
        Long id = userService.register(username, password, pwdCheck);
        return Result.success(id);
    }

    /**
     * 用户登录
     * @param userLoginDTO 用户登录数据传输对象
     * @param request http请求对象，用于设置session
     * @return 登录用户的相关信息
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        if (userLoginDTO == null) {
            return Result.error();
        }
        log.info("用户登录：{}", userLoginDTO);
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        // 检查用户名和密码是否为空
        if (StringUtils.isAnyBlank(username, password)) {
            return Result.error(MessageConstant.USERNAME_OR_PASSWORD_NOT_INPUT);
        }
        User user = userService.login(username, password, request);
        // 不存在对应用户名和密码的用户，则说明用户名或密码错误
        if (user == null) {
            return Result.error(MessageConstant.USERNAME_OR_PASSWORD_ERROR);
        }
        return Result.success(user);
    }

    /**
     * 获取当前登录用户信息
     * @param request http请求对象
     * @return 返回脱敏后的用户信息
     */
    @GetMapping("/current")
    public Result<User> getCurrentUser(HttpServletRequest request) {
        // 使用请求头中的session id来查找存储在服务端的session，并从中获取对应的属性值
        User user = (User) request.getSession().getAttribute(UserConstant.USER_INFO);
        if (user == null) {
            // 用户为空，返回成功，表示查询正常，但用户未登录
            return Result.success();
        }
        log.info("当前用户：{}", user);
        User currentUser = userService.getById(user.getId());
        // 脱敏
        currentUser.setPassword(null);
        return Result.success(currentUser);
    }

    /**
     * 用户退出登录
     * @param request http请求对象，用于获取session
     * @return 返回成功
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        if (request == null) {
            return Result.error();
        }
        // 删除存储在服务端的session
        request.getSession().removeAttribute(UserConstant.USER_INFO);
        return Result.success();
    }
}

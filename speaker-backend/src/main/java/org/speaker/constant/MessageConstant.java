package org.speaker.constant;

/**
 * 信息提示常量类
 */
public interface MessageConstant {

    String USERNAME_OR_PASSWORD_NOT_INPUT       = "用户名和密码均不能为空";
    String USERNAME_OR_PASSWORD_FORMAT_ERROR    = "用户名或密码格式错误";
    String USERNAME_OR_PASSWORD_ERROR           = "用户名或密码错误";
    String PASSWORDS_NOT_CONSISTENT             = "两次输入密码不一致";
    String USERNAME_EXIST                       = "用户名已存在";

    String TITLE_OR_CONTENT_NOT_INPUT           = "标题和正文均不能为空";
    String TITLE_SO_LONG                        = "标题过长";
    String CONTENT_SO_LONG                      = "正文内容过长";
    String POSTER_NOT_FOUND                     = "帖子不存在";
    String ILLEGAL_OPERATION                    = "非法操作";
    String PAGE_QUERY_ERROR                     = "分页查询错误";
}

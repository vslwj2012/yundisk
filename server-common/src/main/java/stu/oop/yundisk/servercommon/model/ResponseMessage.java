package stu.oop.yundisk.servercommon.model;

import java.io.Serializable;

/**
 * @author vega
 */
public class ResponseMessage implements Serializable {
    public static final String LOGIN_SUCCESS ="登录成功!";
    public static final String REPEATED_LOGIN ="该用户已登录!";
    public static final String ERROR_PASS ="账号密码错误，请重新登陆!";
    public static final String EXIST_USER="用户已存在!";
    public static final String REGISTER_SUCCESS="注册成功!";
    public static final String UPDATE_NICKNAME_SUCCESS="昵称修改成功";
    public static final String INTERNET_ERROR="网络异常";
    public static final String EXIST_INTACT_FILE="存在完整文件";
    public static final String NO_EXIST_INTACT_FILE="不存在完整文件";
}

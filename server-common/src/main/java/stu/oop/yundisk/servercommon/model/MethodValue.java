package stu.oop.yundisk.servercommon.model;

import java.io.Serializable;

/**
 * 请求函数的value
 * @author vega
 */
public class MethodValue implements Serializable {
    public static final String LOGIN="login";
    public static final String REGISTER="register";
    public static final String UPDATE_NICKNAME="updateNickname";
    public static final String UPLOAD_EXIST_FILE="uploadExistFile";
    public static final String UPLOAD_FILE="uploadFile";
}

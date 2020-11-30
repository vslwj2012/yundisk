package stu.oop.yundisk.servercommon.model;

import java.io.Serializable;

/**
 * @author vega
 */
public class ErrorCodeConstants implements Serializable {
    public static final String SERVER_START_ERROR="服务器启动失败，请检查端口是否占用!";
    public static final String METHOD_NOT_EXIST="控制器函数不存在";
    public static final String CONNECTION_ERROR="连接服务器失败，请检查网络连接!";
}

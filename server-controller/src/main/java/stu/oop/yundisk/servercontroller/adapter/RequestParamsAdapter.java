package stu.oop.yundisk.servercontroller.adapter;



import stu.oop.yundisk.servercommon.model.Request;
import stu.oop.yundisk.servercommon.model.exception.BusinessException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class RequestParamsAdapter {

    /**
     * 获取函数中的参数名，并将请求中的请求参数封装成object数组以传给invoke函数
     * 即保持客户端发来的变量名与controller中方法的参数名一致即可实现自动调用
     *
     * @param method  需要执行调用的controller中的函数
     * @param request 客户端发来的请求
     * @return 适配后的参数数组
     */
    public static Object[] castToObjectArray(Method method, Request request) {
        Parameter[] parameters = method.getParameters();
        Object[] params = new Object[parameters.length];
        int i = 0;
        Map<String, Object> requestParams = null;
        for (Parameter parameter : parameters) {
            if (request == null) {
                throw new BusinessException("");
            }
            requestParams = request.getParams();
            if (requestParams == null) {
                throw new BusinessException("");
            }
            Object object = requestParams.get(parameter.getName());
            params[i++] = object;
        }
        return params;
    }
}

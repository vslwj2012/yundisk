package stu.oop.yundisk.servercontroller.controller.core;

import org.springframework.context.ApplicationContext;
import stu.oop.yundisk.servercommon.annotation.Controller;
import stu.oop.yundisk.servercommon.annotation.RequestMapping;
import stu.oop.yundisk.servercommon.model.MethodValue;
import stu.oop.yundisk.servercommon.model.Request;
import stu.oop.yundisk.servercommon.model.Response;
import stu.oop.yundisk.servercontroller.adapter.RequestParamsAdapter;
import stu.oop.yundisk.servercontroller.adapter.ResponseAdapter;
import stu.oop.yundisk.servercontroller.thread.EventHandlerThread;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vega
 */
public class DispatcherController {

    private ApplicationContext applicationContext;
    private Request request;
    /**
     * 该dispatcher所在的线程
     */
    private EventHandlerThread eventHandlerThread;

    private Map<String, List<Object>> methodValueClassMap;

    public DispatcherController(ApplicationContext applicationContext, Request request, EventHandlerThread eventHandlerThread) {
        this.applicationContext = applicationContext;
        this.request = request;
        this.eventHandlerThread = eventHandlerThread;
        initHandlerMapping();
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public EventHandlerThread getEventHandlerThread() {
        return eventHandlerThread;
    }

    public void setEventHandlerThread(EventHandlerThread eventHandlerThread) {
        this.eventHandlerThread = eventHandlerThread;
    }

    /**
     * 利用反射将请求根据请求的methodValue定位到对应注解的函数并执行
     */
    public Response execute() {
        Response response = null;
        String methodValue = request.getMethodValue();
        List<Object> list = methodValueClassMap.get(methodValue);
        Method method = (Method) list.get(0);

        if (list != null && list.size() > 0) {
            try {
                //获取函数中的参数名，并将请求中的请求参数封装成object数组以传给invoke函数
                //即保持客户端发来的变量名与controller中方法的参数名一致即可实现自动调用
                Object[] params = RequestParamsAdapter.castToObjectArray(method, request);
                //调用该methodValue对应的方法
                Object resp = null;
                if (methodValue.equals(MethodValue.LOGIN) || methodValue.equals(MethodValue.UPLOAD_FILE)) {
                    params[0] = eventHandlerThread;
                }
                resp = method.invoke(list.get(1), params);
                response = ResponseAdapter.castToResponse(resp);
                return response;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将controller中函数的注解值和函数及函数所在的controller对象形成映射
     * 其中主键为注解值,list集合0位置存放的为函数，1位置存放的对应controller对象
     */
    private void initHandlerMapping() {
        Map map = applicationContext.getBeansWithAnnotation(Controller.class);
        methodValueClassMap = new HashMap<>();

        for (Object controller : map.values()) {
            Method[] methods = controller.getClass().getDeclaredMethods();
            for (Method m : methods) {
                RequestMapping annotation = m.getAnnotation(RequestMapping.class);
                List<Object> list = new ArrayList<>(2);
                list.add(0, m);
                list.add(1, controller);
                methodValueClassMap.put(annotation.value(), list);
            }
        }
    }
}
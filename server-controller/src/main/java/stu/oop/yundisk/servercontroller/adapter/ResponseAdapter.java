package stu.oop.yundisk.servercontroller.adapter;


import stu.oop.yundisk.servercommon.model.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vega
 */
public class ResponseAdapter {
    public static Response castToResponse(Object object) {
        Response response = new Response();
        if (object instanceof Integer) {
            response.setResponseMessage(String.valueOf(object));
        } else if (object instanceof Double) {
            response.setResponseMessage(String.valueOf(object));
        } else if (object instanceof Float) {
            response.setResponseMessage(String.valueOf(object));
        } else if (object instanceof Boolean) {
            response.setResponseMessage(String.valueOf(object));
        } else if (object instanceof Long) {
            response.setResponseMessage(String.valueOf(object));
        } else if (object instanceof Byte) {
            response.setResponseMessage(String.valueOf(object));
        } else if (object instanceof Short) {
            response.setResponseMessage(String.valueOf(object));
        } else if (object instanceof Character) {
            response.setResponseMessage(String.valueOf(object));
        } else if (object instanceof String) {
            //System.out.println("传入的是String");
            response.setResponseMessage((String) object);
        } else if (object instanceof Response) {
            //System.out.println("传入的是response类型");
            response = (Response) object;
        } else {
            String objectName = object.getClass().getName();
            Map<String, Object> params = new HashMap<>();
            params.put(objectName, object);
            response.setParams(params);
        }
        return response;
    }
}

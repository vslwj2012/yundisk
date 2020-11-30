package stu.oop.yundisk.servercommon.model;

import java.io.Serializable;
import java.util.Map;

/**
 * @author vega
 */
public class Response implements Serializable {
    private Map<String,Object> params;
    private String responseMessage;

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}

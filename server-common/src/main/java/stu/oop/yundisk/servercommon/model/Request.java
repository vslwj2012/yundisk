package stu.oop.yundisk.servercommon.model;

import java.io.Serializable;
import java.util.Map;

/**
 * @author vega
 */
public class Request implements Serializable {
    private Map<String,Object> params;
    private String methodValue;

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getMethodValue() {
        return methodValue;
    }

    public void setMethodValue(String methodValue) {
        this.methodValue = methodValue;
    }
}

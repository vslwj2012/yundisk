package stu.oop.yundisk.servercommon.service.queryservice;

import stu.oop.yundisk.servercommon.model.Response;

/**
 * @author vega
 */
public interface LoginService {
    Response login(String username, String password, String ipStr);
}

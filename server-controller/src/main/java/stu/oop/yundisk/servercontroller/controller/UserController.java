package stu.oop.yundisk.servercontroller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import stu.oop.yundisk.servercommon.annotation.Controller;
import stu.oop.yundisk.servercommon.annotation.RequestMapping;
import stu.oop.yundisk.servercommon.entity.User;
import stu.oop.yundisk.servercommon.model.MethodValue;
import stu.oop.yundisk.servercommon.model.Response;
import stu.oop.yundisk.servercommon.model.ResponseMessage;
import stu.oop.yundisk.servercommon.service.queryservice.LoginService;
import stu.oop.yundisk.servercommon.service.transservice.RegisterService;
import stu.oop.yundisk.servercommon.service.transservice.UpdateNickNameService;
import stu.oop.yundisk.servercontroller.thread.EventHandlerThread;


import java.util.Map;

/**
 * @author vega
 */
@Controller
public class UserController {

    @Reference
    private LoginService loginService;

    @Reference
    private RegisterService registerService;

    @Reference
    private UpdateNickNameService updateNickNameService;

    @RequestMapping(value = MethodValue.LOGIN)
    public Response login(EventHandlerThread eventHandlerThread, String username, String password) {
        String ipStr = eventHandlerThread.getIp().toString();
        Response response = loginService.login(username, password, ipStr);
        Map<String, Object> resparams = response.getParams();
        if (resparams != null) {
            User user = (User) resparams.get("user");
            if (user != null) {
                eventHandlerThread.setUser(user);
            }
        }
        return response;
    }

    @RequestMapping(value = MethodValue.REGISTER)
    public String register(User user) {
        boolean flag = registerService.register(user);
        if (flag) {
            return ResponseMessage.REGISTER_SUCCESS;
        }
        return ResponseMessage.EXIST_USER;
    }

    @RequestMapping(value = MethodValue.UPDATE_NICKNAME)
    public String updateNickname(String newNickname, String username) {
        boolean flag = updateNickNameService.updateNickName(newNickname, username);
        if (flag) {
            return ResponseMessage.UPDATE_NICKNAME_SUCCESS;
        }
        return null;
    }
}

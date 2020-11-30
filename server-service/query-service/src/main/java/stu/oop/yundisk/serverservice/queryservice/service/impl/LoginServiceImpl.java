package stu.oop.yundisk.serverservice.queryservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import stu.oop.yundisk.servercommon.entity.File;
import stu.oop.yundisk.servercommon.entity.User;
import stu.oop.yundisk.servercommon.model.RedisConstantKey;
import stu.oop.yundisk.servercommon.model.Response;
import stu.oop.yundisk.servercommon.model.ResponseMessage;
import stu.oop.yundisk.servercommon.service.queryservice.LoginService;
import stu.oop.yundisk.serverservice.queryservice.dao.FileMapper;
import stu.oop.yundisk.serverservice.queryservice.dao.UserMapper;
import stu.oop.yundisk.serverservice.queryservice.utils.Md5Encipher;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author vega
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @param username
     * @param password
     * @return
     */
    @Override
    public Response login(String username, String password, String ipStr) {
        String userId = stringRedisTemplate.opsForValue().get(RedisConstantKey.ONLINE_USER_HEAD + username);
        Response response = new Response();
        Map<String, Object> resParams = new HashMap<>();
        if (userId != null) {
            //用户已登录
            resParams.put("user", null);
            response.setResponseMessage(ResponseMessage.REPEATED_LOGIN);
        } else {
            String md5Password = Md5Encipher.md5Encode(password);
            User user = userMapper.selectUserByUsernameAndPassword(username, md5Password);
            if (user == null) {
                //账号密码错误
                response.setResponseMessage(ResponseMessage.ERROR_PASS);
                return response;
            }
            Set<File> fileSet=fileMapper.selectFilesByUsername(username);
            if (fileSet!=null){
                user.setFileSet(fileSet);
            }
            resParams.put("user", user);
            response.setResponseMessage(ResponseMessage.LOGIN_SUCCESS);
            stringRedisTemplate.opsForValue().setIfAbsent(RedisConstantKey.ONLINE_USER_HEAD + username, ipStr);
        }
        response.setParams(resParams);
        return response;
    }
}
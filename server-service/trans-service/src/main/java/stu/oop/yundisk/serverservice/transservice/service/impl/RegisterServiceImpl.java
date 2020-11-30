package stu.oop.yundisk.serverservice.transservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import stu.oop.yundisk.servercommon.entity.User;
import stu.oop.yundisk.servercommon.model.RedisConstantKey;
import stu.oop.yundisk.servercommon.properties.BaseProperties;
import stu.oop.yundisk.servercommon.service.transservice.RegisterService;
import stu.oop.yundisk.serverservice.transservice.cache.ExistUserCache;
import stu.oop.yundisk.serverservice.transservice.dao.UserMapper;
import stu.oop.yundisk.serverservice.transservice.utils.Md5Encipher;

import java.io.File;
import java.util.Set;

/**
 * @author vega
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private ExistUserCache existUserCache;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(User user) {
        Set<String> existUserSet=existUserCache.getResult();
        if (!existUserSet.contains(user.getUsername())) {
            //该用不户存在
            String md5Password = Md5Encipher.md5Encode(user.getPassword());
            user.setPassword(md5Password);
            user.setNickname(user.getUsername());
            user.setSpace(0f);
            int flag = userMapper.insert(user);
            if (flag == 1) {
                //注册成功
                existUserCache.add(user.getUsername());
                File file = new File(BaseProperties.SERVER_FILE_PATH + user.getUsername());
                if (!file.exists())
                    file.mkdir();
                return true;
            }
        }
        return false;
    }
}

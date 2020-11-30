package stu.oop.yundisk.serverservice.transservice.cache;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import stu.oop.yundisk.servercommon.annotation.Cache;
import stu.oop.yundisk.servercommon.cache.SetServerCache;
import stu.oop.yundisk.servercommon.entity.User;
import stu.oop.yundisk.servercommon.model.RedisConstantKey;
import stu.oop.yundisk.servercommon.service.queryservice.SelectAllUsersService;

import java.util.Set;

@Cache
public class ExistUserCache implements SetServerCache<String> {

    @Autowired
    private RedisTemplate redisTemplates;

    @Reference
    private SelectAllUsersService selectAllUsersService;

    @Override
    public Set<String> getResult() {
        return redisTemplates.opsForSet().members(RedisConstantKey.EXIST_USER_SET);
    }

    @Override
    public void loadCache() {
        Set<User> userSet = selectAllUsersService.getAllUsers();
        for (User user : userSet) {
            redisTemplates.opsForSet().add(RedisConstantKey.EXIST_USER_SET, user.getUsername());
        }
    }

    @Override
    public void add(String value) {
        redisTemplates.opsForSet().add(RedisConstantKey.EXIST_USER_SET, value);
    }

    @Override
    public void del(String value) {
        redisTemplates.opsForSet().remove(RedisConstantKey.EXIST_USER_SET, value);
    }

    @Override
    public void update(String value) {
        add(value);
    }
}

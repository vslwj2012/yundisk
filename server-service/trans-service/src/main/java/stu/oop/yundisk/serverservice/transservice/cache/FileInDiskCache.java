package stu.oop.yundisk.serverservice.transservice.cache;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import stu.oop.yundisk.servercommon.annotation.Cache;
import stu.oop.yundisk.servercommon.cache.SetServerCache;
import stu.oop.yundisk.servercommon.entity.File;
import stu.oop.yundisk.servercommon.model.RedisConstantKey;
import stu.oop.yundisk.servercommon.service.queryservice.SelectAllFilesService;

import java.util.Set;

@Cache
public class FileInDiskCache implements SetServerCache<File> {

    @Autowired
    private RedisTemplate redisTemplates;

    @Reference
    private SelectAllFilesService getAllFilesService;

    @Override
    public Set<File> getResult() {
        return redisTemplates.opsForSet().members(RedisConstantKey.ALL_FILE_SET);
    }

    @Override
    public void loadCache() {
        Set<File> fileSet = getAllFilesService.getAllFiles();
        for (File file : fileSet) {
            redisTemplates.opsForSet().add(RedisConstantKey.ALL_FILE_SET, file);
        }
    }

    @Override
    public void add(File value) {
        redisTemplates.opsForSet().add(RedisConstantKey.ALL_FILE_SET, value);
    }

    @Override
    public void del(File value) {
        redisTemplates.opsForSet().remove(RedisConstantKey.ALL_FILE_SET, value);
    }

    @Override
    public void update(File value) {
        add(value);
    }
}

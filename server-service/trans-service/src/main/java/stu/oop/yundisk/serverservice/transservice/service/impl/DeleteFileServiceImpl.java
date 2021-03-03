package stu.oop.yundisk.serverservice.transservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import stu.oop.yundisk.servercommon.entity.File;
import stu.oop.yundisk.servercommon.service.transservice.DeleteFileService;
import stu.oop.yundisk.serverservice.transservice.dao.FileMapper;
import stu.oop.yundisk.serverservice.transservice.dao.UserMapper;

@Service
public class DeleteFileServiceImpl implements DeleteFileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean deleteFile(File file) {
        if (fileMapper.deleteFileByFilePath(file.getFilepath())==1){
            if (userMapper.updateUserSpaceByUsername(file.getUsername(),-file.getFilesize())==1){
                return true;
            }
        }
        return false;
    }
}

package stu.oop.yundisk.serverservice.queryservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import stu.oop.yundisk.servercommon.entity.File;
import stu.oop.yundisk.servercommon.service.queryservice.SelectAllFilesService;
import stu.oop.yundisk.serverservice.queryservice.dao.FileMapper;

import java.util.Set;

@Service
public class SelectAllFilesServerImpl implements SelectAllFilesService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public Set<File> getAllFiles() {
        return fileMapper.selectAllFiles();
    }
}

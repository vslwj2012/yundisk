package stu.oop.yundisk.serverservice.queryservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import stu.oop.yundisk.servercommon.service.queryservice.SelectFileUpStatusService;
import stu.oop.yundisk.serverservice.queryservice.dao.FileMapper;

@Service
public class SelectFileUpStatusServiceImpl implements SelectFileUpStatusService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public Integer getFileUpStatus(String username, String md5) {
        Integer upStatus = fileMapper.selectUpStatusByUsernameAndMd5(username, md5);
        return upStatus == null ? 0 : upStatus;
    }
}

package stu.oop.yundisk.serverservice.queryservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import stu.oop.yundisk.servercommon.entity.User;
import stu.oop.yundisk.servercommon.service.queryservice.SelectAllUsersService;
import stu.oop.yundisk.serverservice.queryservice.dao.UserMapper;

import java.util.Set;

@Service
public class SelectAllUsersServiceImpl implements SelectAllUsersService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Set<User> getAllUsers() {
        return userMapper.selectAllUser();
    }
}

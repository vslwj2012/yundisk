package stu.oop.yundisk.serverservice.transservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import stu.oop.yundisk.servercommon.service.transservice.UpdateNickNameService;
import stu.oop.yundisk.serverservice.transservice.dao.UserMapper;

@Service
public class UpdateNickNameServiceImpl implements UpdateNickNameService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean updateNickName(String newNickName, String username) {
        int flag = userMapper.updateNickNameByUsernaem(newNickName, username);
        if (flag == 1) {
            return true;
        }
        return false;
    }
}

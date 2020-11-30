package stu.oop.yundisk.servercommon.service.queryservice;

import stu.oop.yundisk.servercommon.entity.User;

import java.util.Set;

public interface SelectAllUsersService {
    Set<User> getAllUsers();
}

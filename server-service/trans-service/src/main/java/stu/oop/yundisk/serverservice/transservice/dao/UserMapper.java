package stu.oop.yundisk.serverservice.transservice.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import stu.oop.yundisk.servercommon.entity.User;

public interface UserMapper {
    int insert(User record);

    @Update("update yun_user set nickname=#{newNickName} where username=#{username}")
    int updateNickNameByUsernaem(String newNickName, String username);

    @Update("update yun_user set space=space+#{fileSize} where username=#{username}")
    int updateUserSpaceByUsername(String username,long fileSize);
}
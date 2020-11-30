package stu.oop.yundisk.serverservice.queryservice.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import stu.oop.yundisk.servercommon.entity.User;

import java.util.Set;

/**
 * @author vega
 */
public interface UserMapper {

    @Select("select * from yun_user where username=#{username} and password=#{password}")
    User selectUserByUsernameAndPassword( String username, String password);

    @Select("select * from yun_user")
    Set<User> selectAllUser();
}
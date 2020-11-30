package stu.oop.yundisk.serverservice.queryservice.dao;


import org.apache.ibatis.annotations.Select;
import stu.oop.yundisk.servercommon.entity.File;

import java.util.Set;

/**
 * @author vega
 */
public interface FileMapper {
    @Select("select * from user_file where username=#{username}")
    Set<File> selectFilesByUsername(String username);

    @Select("select * from user_file")
    Set<File> selectAllFiles();

    @Select("select upstatus from user_file where username=#{username} and md5=#{md5}")
    Integer selectUpStatusByUsernameAndMd5(String username, String md5);
}
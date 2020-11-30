package stu.oop.yundisk.serverservice.transservice.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import stu.oop.yundisk.servercommon.entity.File;

public interface FileMapper {

    @Insert("insert into user_file (filename, filetype, filesize, filepath, username,filepath_guide,md5,upstatus,localpath) " +
            "values (#{filename},#{filetype}, #{filesize}, #{filepath}, #{username}, #{filepathGuide}, #{md5}, #{upstatus}, #{localpath})")
    int uploadFile(File file);

    @Update("update user_file set upstatus=#{upStatus} where fileId=#{fileId}")
    int updateFileUpStatusByFileId(int fileId,int upStatus);
}
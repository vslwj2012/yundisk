package stu.oop.yundisk.servercommon.service.queryservice;

public interface SelectFileUpStatusService {
    Integer getFileUpStatus(String username, String md5);
}

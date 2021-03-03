package stu.oop.yundisk.servercommon.service.transservice;

import stu.oop.yundisk.servercommon.entity.File;
import stu.oop.yundisk.servercommon.model.Response;

public interface DeleteFileService {
    boolean deleteFile(File file);
}

package stu.oop.yundisk.servercommon.service.transservice;

import stu.oop.yundisk.servercommon.entity.File;

import java.io.ObjectInputStream;

public interface UploadFileService {
    void upLoadFile(File files, ObjectInputStream objectInputStream) throws Exception;
}

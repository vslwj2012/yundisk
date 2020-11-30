package stu.oop.yundisk.servercommon.service.queryservice;

import stu.oop.yundisk.servercommon.entity.File;

import java.util.Set;

public interface SelectAllFilesService {
    Set<File> getAllFiles();
}

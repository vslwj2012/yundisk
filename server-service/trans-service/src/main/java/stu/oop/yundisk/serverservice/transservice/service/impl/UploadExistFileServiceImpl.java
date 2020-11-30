package stu.oop.yundisk.serverservice.transservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import stu.oop.yundisk.servercommon.entity.File;
import stu.oop.yundisk.servercommon.model.Response;
import stu.oop.yundisk.servercommon.model.ResponseMessage;
import stu.oop.yundisk.servercommon.service.transservice.UploadExistFileService;
import stu.oop.yundisk.serverservice.transservice.cache.FileInDiskCache;
import stu.oop.yundisk.serverservice.transservice.dao.FileMapper;
import stu.oop.yundisk.serverservice.transservice.dao.UserMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class UploadExistFileServiceImpl implements UploadExistFileService {
    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileInDiskCache fileInDiskCache;

    @Override
    public Response upLoadExistFile(File files) {
        Response response = new Response();
        String filemd5 = files.getMd5();
        Set<File> filesSet = fileInDiskCache.getResult();
        if (filesSet != null && filesSet.size() != 0) {
            //判断是否有相同内容的文件存在在服务器中
            for (File f : filesSet) {
                //如果存在与上传文件md5值相同的文件，则将云盘中的已存在的上传完毕的文件路径赋给新上传文件的引用路径
                if (f.getMd5().equals(filemd5) && f.getUpstatus() == -1) {
                    files.setFilepathGuide(f.getFilepath());
                    files.setUpstatus(-1);
                    fileMapper.uploadFile(files);
                    userMapper.updateUserSpaceByUsername(files.getUsername(), files.getFilesize());
                    response.setResponseMessage(ResponseMessage.EXIST_INTACT_FILE);
                    Map<String, Object> params = new HashMap<>();
                    params.put("file", files);
                    response.setParams(params);
                    return response;
                }
            }
        }
        response.setResponseMessage(ResponseMessage.NO_EXIST_INTACT_FILE);
        return response;
    }
}

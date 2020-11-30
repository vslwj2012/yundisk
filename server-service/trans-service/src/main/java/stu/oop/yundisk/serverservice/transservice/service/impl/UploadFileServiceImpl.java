package stu.oop.yundisk.serverservice.transservice.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import stu.oop.yundisk.servercommon.entity.File;
import stu.oop.yundisk.servercommon.service.queryservice.SelectFileUpStatusService;
import stu.oop.yundisk.servercommon.service.transservice.UploadFileService;
import stu.oop.yundisk.serverservice.transservice.cache.FileInDiskCache;
import stu.oop.yundisk.serverservice.transservice.dao.FileMapper;
import stu.oop.yundisk.serverservice.transservice.dao.UserMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Set;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileInDiskCache fileInDiskCache;

    @Reference
    private SelectFileUpStatusService selectFileUpStatusService;

    /**
     * 单位上传长度
     */
    private static final int TRANS_UNIT_LENGTH = 1024;

    @Override
    public void upLoadFile(File files, ObjectInputStream in) {
        String filepath = files.getFilepath();

        files.setUpstatus(selectFileUpStatusService.getFileUpStatus(files.getUsername(), files.getMd5()));
        //用来记录当前传了第几次
        int n = 0;
        //标记文件是否上传过，若上传过该值大于0
        int upStatus = 0;
        //设置一个读取开始点，若是第一次传，则从0开始，若不是第一次传，则从上次停止的地方开始
        int start = 0;
        try {
            FileOutputStream fout = new FileOutputStream(filepath, true);
            long filesize = files.getFilesize();
            long transCount = filesize / TRANS_UNIT_LENGTH;
            upStatus = files.getUpstatus();
            if (upStatus > 0) {
                start = upStatus;
            }
            for (n = start; n <= transCount; n++) {
                byte[] bs = new byte[TRANS_UNIT_LENGTH];
                if (in != null) {
                    int length = in.read(bs);
                    fout.write(bs, 0, length);
                    fout.flush();
                }
            }
            fout.close();
            if (n >= transCount) {
                //-1表示文件已完全上传成功
                n = -1;
            }
        } catch (Exception e) {
            //更新捕获到异常时的上传次数
            System.out.println("传输中断");
        } finally {
            System.out.println(n);
            files.setUpstatus(n);
            if (upStatus > 0) {
                //若不是第一次上传
                fileMapper.updateFileUpStatusByFileId(files.getFileid(), files.getUpstatus());
            } else {
                //第一次上传
                fileMapper.uploadFile(files);
            }
            userMapper.updateUserSpaceByUsername(files.getUsername(), (n - start) * TRANS_UNIT_LENGTH);
            fileInDiskCache.update(files);
        }
    }
}
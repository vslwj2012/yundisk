package stu.oop.yundisk.servercontroller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import stu.oop.yundisk.servercommon.annotation.Controller;
import stu.oop.yundisk.servercommon.annotation.RequestMapping;
import stu.oop.yundisk.servercommon.entity.File;
import stu.oop.yundisk.servercommon.model.MethodValue;
import stu.oop.yundisk.servercommon.model.Response;
import stu.oop.yundisk.servercommon.model.ResponseMessage;
import stu.oop.yundisk.servercommon.service.transservice.UploadExistFileService;
import stu.oop.yundisk.servercommon.service.transservice.UploadFileService;
import stu.oop.yundisk.servercontroller.thread.EventHandlerThread;

import java.io.IOException;

@Controller
public class FileController {

    @Reference
    private UploadExistFileService uploadExistFileService;

    @Reference
    private UploadFileService uploadFileService;

    @RequestMapping(value = MethodValue.UPLOAD_EXIST_FILE)
    public Response upLoadExistFile(File file){
        return uploadExistFileService.upLoadExistFile(file);
    }

    @RequestMapping(value = MethodValue.UPLOAD_FILE)
    public String upLoadFile(EventHandlerThread eventHandlerThread, File file){
        try {
            uploadFileService.upLoadFile(file,eventHandlerThread.getIn());
        } catch (IOException e) {
            return ResponseMessage.INTERNET_ERROR;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

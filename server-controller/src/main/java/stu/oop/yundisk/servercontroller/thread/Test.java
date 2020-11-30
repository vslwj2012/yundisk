package stu.oop.yundisk.servercontroller.thread;

import stu.oop.yundisk.servercommon.model.Response;
import stu.oop.yundisk.servercontroller.adapter.ResponseAdapter;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Response response=new Response();
        Object object=new Object();
        ResponseAdapter.castToResponse(object);
    }
}

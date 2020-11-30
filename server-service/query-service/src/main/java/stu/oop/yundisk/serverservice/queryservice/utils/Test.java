package stu.oop.yundisk.serverservice.queryservice.utils;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Test<T extends List & Serializable> {

    public static void main(String[] args) {
        String str="ABCDE";
        str.substring(3);
        str.concat("XYZ");
        System.out.print(str);
        String s1="hello";
        if(s1=="hello"){
            System.out.println("s1 = \"hello\"");
        }else{
            System.out.println("s1 !=hello");
        }
    }
}

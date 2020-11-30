package stu.oop.yundisk.client.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 用于辅助实现功能的一些方法的工具类
 */
public class YunUtils {
    /**
     * 传入一个文件名，通过文件名获取该文件中最后一个“.”在什么位置，用于确定文件类型
     * @param filename 传入的文件名
     * @return 返回最后一个点的下标
     */
    public static int getLastPoint(String filename)
    {
        int count=0;
        for (int n=0;n<filename.length();n++)
        {
            if (filename.charAt(n)=='.')
            {
                count++;
            }
        }
        int num=0;
        int n;
        for (n=0;n<filename.length();n++)
        {
            if (filename.charAt(n)=='.')
            {
                num++;
                {
                    if (num==count)
                    {
                        break;
                    }
                }
            }
        }
        return n;
    }

    /**
     * 获取文件的md5值
     * @param path 需要获取md5值的文件的路径
     * @return 文件的md5值
     */
    public static String getMD5(String path) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[1024*1024*5];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi.toString(16);
    }
}

package stu.oop.yundisk.serverservice.transservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import stu.oop.yundisk.servercommon.utils.CacheUtil;

/**
 * @author vega
 */
@SpringBootApplication
@MapperScan(value = {"stu.oop.yundisk.serverservice.transservice.dao"})
public class TransServiceApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext=SpringApplication.run(TransServiceApplication.class, args);
        CacheUtil.initCache(applicationContext);
    }
}

package stu.oop.yundisk.serverservice.queryservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import stu.oop.yundisk.serverservice.queryservice.utils.Md5Encipher;

/**
 * @author vega
 */
@SpringBootApplication
@MapperScan(value = {"stu.oop.yundisk.serverservice.queryservice.dao"})
public class QueryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryServiceApplication.class, args);
    }

}

package stu.oop.yundisk.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import stu.oop.yundisk.client.view.LoginFrame;

import java.io.File;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        System.setProperty("java.awt.headless", "false");
        File file=new File("D:\\YunDiskDownload");
        if (!file.exists()) {
            file.mkdir();
        }
        new LoginFrame().setVisible(true);
    }

}

package stu.oop.yundisk.servercontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import stu.oop.yundisk.servercommon.model.ErrorCodeConstants;
import stu.oop.yundisk.servercommon.properties.BaseProperties;
import stu.oop.yundisk.servercontroller.thread.EventHandlerThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class ServerControllerApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext=SpringApplication.run(ServerControllerApplication.class, args);

        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(BaseProperties.SERVER_PORT);
        } catch (IOException e) {
            System.out.println(ErrorCodeConstants.SERVER_START_ERROR);
        }

        while (true){
            try {
                Socket connection=serverSocket.accept();
                InetAddress ip=connection.getInetAddress();
                ObjectOutputStream out=new ObjectOutputStream(connection.getOutputStream());
                ObjectInputStream in=new ObjectInputStream(connection.getInputStream());
                EventHandlerThread eventHandlerThread=new EventHandlerThread(applicationContext,out,in);
                eventHandlerThread.setIp(ip);
                eventHandlerThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

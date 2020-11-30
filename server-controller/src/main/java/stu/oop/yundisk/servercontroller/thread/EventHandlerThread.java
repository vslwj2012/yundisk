package stu.oop.yundisk.servercontroller.thread;

import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import stu.oop.yundisk.servercommon.entity.User;
import stu.oop.yundisk.servercommon.model.ErrorCodeConstants;
import stu.oop.yundisk.servercommon.model.RedisConstantKey;
import stu.oop.yundisk.servercommon.model.Request;
import stu.oop.yundisk.servercommon.model.Response;
import stu.oop.yundisk.servercontroller.controller.core.DispatcherController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

public class EventHandlerThread extends Thread {

    private ApplicationContext applicationContext;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private User user;
    private InetAddress ip;
    private StringRedisTemplate stringRedisTemplate;

    public EventHandlerThread() {
        this.stringRedisTemplate = applicationContext.getBean(StringRedisTemplate.class);
    }

    public EventHandlerThread(ApplicationContext applicationContext, ObjectOutputStream out, ObjectInputStream in) {
        this.applicationContext = applicationContext;
        this.out = out;
        this.in = in;
        this.stringRedisTemplate = applicationContext.getBean(StringRedisTemplate.class);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Request request = (Request) in.readObject();
                DispatcherController dispatcherController = new DispatcherController(applicationContext, request, this);
                Response response = dispatcherController.execute();
                if (response == null) {
                    System.out.println(ErrorCodeConstants.METHOD_NOT_EXIST);
                    continue;
                }
                out.writeObject(response);
                out.flush();
                out.reset();
            }
        } catch (IOException e) {
            System.out.println(stringRedisTemplate.opsForValue().get(RedisConstantKey.ONLINE_USER_HEAD + user.getUsername()) + "已退出");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //用户退出
            if (user != null) {
                stringRedisTemplate.delete(RedisConstantKey.ONLINE_USER_HEAD + user.getUsername());
            }
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package stu.oop.yundisk.servercommon.utils;

import org.springframework.context.ApplicationContext;
import stu.oop.yundisk.servercommon.annotation.Cache;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class CacheUtil {
    public static void initCache(ApplicationContext applicationContext){
        Map cacheMap=applicationContext.getBeansWithAnnotation(Cache.class);
        for (Object cache:cacheMap.values()){
            try {
                Method method=cache.getClass().getDeclaredMethod("loadCache");
                method.invoke(cache);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}

package stu.oop.yundisk.servercommon.annotation;

import java.lang.annotation.*;

/**
 * @author vega
 */
@Documented//会被javadoc命令识别
@Retention(RetentionPolicy.RUNTIME)//作用时期，运行时
@Target({ElementType.TYPE,ElementType.METHOD})//作用域为类和函数上
public @interface RequestMapping {
    String value();
}

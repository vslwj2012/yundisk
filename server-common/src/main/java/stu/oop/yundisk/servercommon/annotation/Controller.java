package stu.oop.yundisk.servercommon.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author vega
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface Controller {
}

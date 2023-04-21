package cn.king.canal.client.spring.boot.framework.annotation;

import org.apache.commons.lang.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CanalTable {
       
    String value() default StringUtils.EMPTY;

}

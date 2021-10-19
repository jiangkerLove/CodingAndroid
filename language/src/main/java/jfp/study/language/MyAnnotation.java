package jfp.study.language;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 设置注解目标
@Target({ElementType.TYPE})
// 运行时注解
@Retention(RetentionPolicy.RUNTIME)
// 表示这个注解是否会出现在javadoc中
@Documented
// 表示该注解可以继承
@Inherited
public @interface MyAnnotation {
    String [] value() default "unknown";
}

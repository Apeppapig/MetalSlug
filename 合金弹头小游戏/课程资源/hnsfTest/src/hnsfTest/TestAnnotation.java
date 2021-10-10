package hnsfTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})  //定义TestAnnotation可以解释 方法
@Retention(RetentionPolicy.RUNTIME) //这个注解是运行时起作用
public @interface TestAnnotation {

}

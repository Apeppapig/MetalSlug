package hnsfTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})  //����TestAnnotation���Խ��� ����
@Retention(RetentionPolicy.RUNTIME) //���ע��������ʱ������
public @interface TestAnnotation {

}

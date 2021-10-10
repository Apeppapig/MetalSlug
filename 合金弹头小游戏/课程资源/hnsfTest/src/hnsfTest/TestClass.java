package hnsfTest;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class TestClass {
	
	/**
	 * ��̬���÷�����
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws Exception {
//		ͨ���ļ�·������ȡ�����
		String url="C:\\renjj\\Java\\eclipseWork\\WorkSpacesSW\\hnsfTest\\src\\hnsfTest";
		File file=new File(url);
//		��̬�����ද̬ʵ��������
		File[] listFiles = file.listFiles();
		String [] names=new String[3];
//		for(int i=0;i<listFiles.length;i++) {
////			System.out.println(listFiles[i]);
//			File f=listFiles[i];
//			System.out.println(f.getName());
//		}
		names[0]="hnsfTest.Student";
		names[1]="hnsfTest.TestClass";
		names[2]="hnsfTest.TestMain";
//		��������֣��Ϳ���ͨ���������ʵ����
		for(String str:names) {
			Class<?> className = Class.forName(str);
//			����ʵ����û������     newInstance()��������ǵ���Ĭ�Ϲ���
//			System.out.println(className.newInstance());
//			ֻҪ�ҵ�ͨ��io���Զ�д��һ��.java�ļ���Ȼ��ͨ�^JVM����ΪClass
//			�Ϳ��Զ�̬��ִ�������,����ִ�����еķ���
			Method[] methods = className.getMethods();//�õ�����������еķ���
			for(int i=0;i<methods.length;i++) {
//				System.out.println(methods[i]);
				Annotation[] annotations = methods[i].getAnnotations();//�õ�ÿ��������ע��
				for(int j=0;j<annotations.length;j++) {
					Annotation aa =annotations[j];
					if(aa instanceof TestAnnotation) {
						
//						ִ�������������Ҫ2������
						methods[i].invoke(className.newInstance());
					}
				}
			
			}
			/**
			 * ����������������顣ע��
			 * 
			 * 
			 */
			
		}
		
		
	}

}

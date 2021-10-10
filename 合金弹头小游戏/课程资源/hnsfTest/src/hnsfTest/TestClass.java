package hnsfTest;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class TestClass {
	
	/**
	 * 动态调用方法。
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws Exception {
//		通过文件路径来获取类对象
		String url="C:\\renjj\\Java\\eclipseWork\\WorkSpacesSW\\hnsfTest\\src\\hnsfTest";
		File file=new File(url);
//		动态加载类动态实例化对象
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
//		有类的名字，就可以通过反射进行实例化
		for(String str:names) {
			Class<?> className = Class.forName(str);
//			可以实例化没个对象     newInstance()这个方法是调用默认构造
//			System.out.println(className.newInstance());
//			只要我等通过io流自动写出一个.java文件，然後通過JVM编译为Class
//			就可以动态的执行这个类,并且执行其中的方法
			Method[] methods = className.getMethods();//得到这个类下所有的方法
			for(int i=0;i<methods.length;i++) {
//				System.out.println(methods[i]);
				Annotation[] annotations = methods[i].getAnnotations();//得到每个方法的注解
				for(int j=0;j<annotations.length;j++) {
					Annotation aa =annotations[j];
					if(aa instanceof TestAnnotation) {
						
//						执行这个方法，需要2个参数
						methods[i].invoke(className.newInstance());
					}
				}
			
			}
			/**
			 * 最多框架里面做的事情。注解
			 * 
			 * 
			 */
			
		}
		
		
	}

}

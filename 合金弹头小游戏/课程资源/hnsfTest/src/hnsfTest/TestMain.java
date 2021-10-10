package hnsfTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestMain {
	/**
	 * 对象的排除
	 * 
	 * @param args
	 */
	@TestAnnotation
	public void testC() {
		System.out.println("testc");
	}
	
	
	
	
	public static void main(String[] args) {
		test2();
	}
	public static void test2() {
		List<Student> list=new ArrayList<>();
		list.add(new Student(18,"任俊杰"));
		list.add(new Student(19,"陈恒法"));
		list.add(new Student(17,"刘诗情"));
		list.add(new Student(21,"钟文宇"));
		list.add(new Student(21,"郑亿鑫"));
		
		/**
		 * 1.自然排序是按名字排序
		 * 2.还需要按年龄排序
		 * 3.还需要按年龄小到大，名字按自然顺序
		 * 比较器
		 */
		System.out.println(list.toString());
		Collections.sort(list);
		System.out.println("名字排序:"+list.toString());
		Collections.sort(list, Student.getComparatorByAge());
		System.out.println("年龄排序:"+list.toString());
		Collections.sort(list, Student.getComparatorByAgeAndName());
		System.out.println("年龄名字排序:"+list.toString());
	}
	
	
	
	public static void test1() {
		List<String> list=new ArrayList<>();
		list.add("赵伟竣");
		list.add("任俊杰");
		list.add("陈恒法");
		list.add("刘诗情");
		list.add("郑亿鑫");
		System.out.println(list.toString());
//		java的排序和c的排序不一样
		Collections.sort(list);
		System.out.println(list.toString());
		System.out.println((int)'任');
		System.out.println((int)'刘');
		System.out.println((int)'陈');
	}

}

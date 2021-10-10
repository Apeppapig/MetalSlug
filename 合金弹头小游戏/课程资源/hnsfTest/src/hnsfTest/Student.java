package hnsfTest;

import java.util.Comparator;

@TestAnnotation
public class Student implements Comparable<Student>{
	private int age;
	private String name;
	public Student() {}
	public Student(int age,String name) {
		this.age=age;
		this.name=name;
	}
	@Override
	public String toString() {
		return "Student [age=" + age + ", name=" + name + "]";
	}
	/**
	 * Ĭ������. ��Ȼ����
	 */
	@Override
	public int compareTo(Student o) {
		return this.name.compareTo(o.name);
	}
//	ʹ���ڲ������������
	
	public static Comparator<Student> getComparatorByAge(){
//		�ڲ���   �ܽ��java�ĵ��̳�����
		return new Comparator<Student>() {
			public int compare(Student o1, Student o2) {
				return o1.age -o2.age;
			}
		};
	}
	public static Comparator<Student> getComparatorByAgeAndName(){
		return new Comparator<Student>() {
			public int compare(Student o1, Student o2) {
				if(o1.age ==o2.age) {
					return o2.name.compareTo(o1.name);
				}
				return o1.age -o2.age;
			}
		};
	}
//	@TestAnnotation  //�൱�ڶ��������һ�����֤
	public void testA() {
		System.out.println("testA");
	}
	@TestAnnotation
	public void testB() {
		System.out.println("testB");
	}
}







package hnsfTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestMain {
	/**
	 * ������ų�
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
		list.add(new Student(18,"�ο���"));
		list.add(new Student(19,"�º㷨"));
		list.add(new Student(17,"��ʫ��"));
		list.add(new Student(21,"������"));
		list.add(new Student(21,"֣����"));
		
		/**
		 * 1.��Ȼ�����ǰ���������
		 * 2.����Ҫ����������
		 * 3.����Ҫ������С�������ְ���Ȼ˳��
		 * �Ƚ���
		 */
		System.out.println(list.toString());
		Collections.sort(list);
		System.out.println("��������:"+list.toString());
		Collections.sort(list, Student.getComparatorByAge());
		System.out.println("��������:"+list.toString());
		Collections.sort(list, Student.getComparatorByAgeAndName());
		System.out.println("������������:"+list.toString());
	}
	
	
	
	public static void test1() {
		List<String> list=new ArrayList<>();
		list.add("��ΰ��");
		list.add("�ο���");
		list.add("�º㷨");
		list.add("��ʫ��");
		list.add("֣����");
		System.out.println(list.toString());
//		java�������c������һ��
		Collections.sort(list);
		System.out.println(list.toString());
		System.out.println((int)'��');
		System.out.println((int)'��');
		System.out.println((int)'��');
	}

}

package tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileFilter;

public class SimpleFileFilter  extends FileFilter {
	
    private List<String> all_extension=null;        //�洢����ĺ�׺��
    private String remark="�ļ����͵�����";
   
    public SimpleFileFilter(){
        all_extension=new ArrayList<String>();
    }
       //������췽������һ����׺����һ������
    public SimpleFileFilter(String ext,String des){
        this();        //���ñ����еĹ��췽��
        all_extension.add(ext);
        remark=des;
    }
    public SimpleFileFilter(String[] ext,String des){
        this();
        all_extension.addAll(Arrays.asList(ext));  //��extת��ΪCollection����
        remark=des;
    }
   
    //���һ����׺����һ������
    public void addExtension(String ext){
        all_extension.add(ext);
    }
    public void setDescription(String des){
        remark=des;
    }
   
    //���һ�������׺��
    public void addExtension(String[] ext){
        all_extension.addAll(Arrays.asList(ext));
    }
   
    //���һ�������׺����һ������
    public void addExtension(String[] ext,String des){
        all_extension.addAll(Arrays.asList(ext));
        remark=des;
    }
   
    @Override
    public boolean accept(File f) {
        String file_name=f.getName().toLowerCase();
        if(f.isDirectory()){
            return true;
        }
        //���·����Ե��е㷱��������

        for(String ext:all_extension){
            if(file_name.endsWith(ext)){
                return true;
            }
        }
        return false;
    }
   
    @Override
    public String getDescription() {
        return remark;
    }
	
}
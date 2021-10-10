package tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileFilter;

public class SimpleFileFilter  extends FileFilter {
	
    private List<String> all_extension=null;        //存储加入的后缀名
    private String remark="文件类型的描述";
   
    public SimpleFileFilter(){
        all_extension=new ArrayList<String>();
    }
       //这个构造方法接收一个后缀名和一个描述
    public SimpleFileFilter(String ext,String des){
        this();        //调用本类中的构造方法
        all_extension.add(ext);
        remark=des;
    }
    public SimpleFileFilter(String[] ext,String des){
        this();
        all_extension.addAll(Arrays.asList(ext));  //把ext转换为Collection类型
        remark=des;
    }
   
    //添加一个后缀名和一个描述
    public void addExtension(String ext){
        all_extension.add(ext);
    }
    public void setDescription(String des){
        remark=des;
    }
   
    //添加一个数组后缀名
    public void addExtension(String[] ext){
        all_extension.addAll(Arrays.asList(ext));
    }
   
    //添加一个数组后缀名和一个描述
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
        //以下方法显得有点繁琐，不用

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
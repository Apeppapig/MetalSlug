package tools;

import java.io.File;


import javax.swing.filechooser.FileFilter;

public class MyFileFilter  extends FileFilter {
	
	 private String extension=".文件后缀";    //存储加入的后缀名
   
   
    public MyFileFilter(String extension){
       this.extension = extension;
    }
       //这个构造方法接收一个后缀名和一个描述    
   
    
   
    @Override
    public boolean accept(File f) {
        String file_name=f.getName().toLowerCase();
        if(f.isDirectory()){
            return true;
        }
        
        //以下方法判断后缀    
         if(file_name.endsWith(this.extension)){
                return true;
            }
               
        return false;
    }
   
    @Override
    public String getDescription() {
        return "jpg文件";
    }
	
}
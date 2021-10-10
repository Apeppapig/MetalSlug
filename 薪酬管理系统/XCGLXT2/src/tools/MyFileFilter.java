package tools;

import java.io.File;


import javax.swing.filechooser.FileFilter;

public class MyFileFilter  extends FileFilter {
	
	 private String extension=".�ļ���׺";    //�洢����ĺ�׺��
   
   
    public MyFileFilter(String extension){
       this.extension = extension;
    }
       //������췽������һ����׺����һ������    
   
    
   
    @Override
    public boolean accept(File f) {
        String file_name=f.getName().toLowerCase();
        if(f.isDirectory()){
            return true;
        }
        
        //���·����жϺ�׺    
         if(file_name.endsWith(this.extension)){
                return true;
            }
               
        return false;
    }
   
    @Override
    public String getDescription() {
        return "jpg�ļ�";
    }
	
}
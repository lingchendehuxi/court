package com.court.oa.project.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by MateBook D on 2018/7/24.
 */

public class FileUtils {
    private String SDCardRoot;



    public FileUtils(){

        //得到当前外部存储设备的目录

        SDCardRoot= Environment.getExternalStorageDirectory()+ File.separator;

        //File.separator为文件分隔符”/“,方便之后在目录下创建文件

    }



    //在SD卡上创建文件

    public File createFileInSDCard(String fileName,String dir) throws IOException {

        File file=new File(SDCardRoot+dir+File.separator+fileName);

        file.createNewFile();

        return file;

    }



    //在SD卡上创建目录

    public File createSDDir(String dir)throws IOException{

        File dirFile=new File(SDCardRoot+dir);

        dirFile.mkdir();//mkdir()只能创建一层文件目录，mkdirs()可以创建多层文件目录

        return dirFile;

    }



    //判断文件是否存在

    public boolean isFileExist(String fileName,String dir){

        File file=new File(SDCardRoot+dir+File.separator+fileName);

        return file.exists();

    }
    public static Intent getTextFileIntent( String param, boolean paramBoolean){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean){
            Uri uri1 = Uri.parse(param );
            intent.setDataAndType(uri1, "text/plain");
        }else{
            Uri uri2 = Uri.fromFile(new File(param ));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

}

package com.example.wy.daylife.tools;

import android.content.Context;
import android.os.Environment;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by wy on 2016/10/25.
 */

public class StatusWriterTool {

    private static String fileName="status";

    public static void writeStatus(String response){
        try{
            File file=new File(Environment.getExternalStorageDirectory(), fileName);
            FileOutputStream fout = new FileOutputStream(file);
            byte [] bytes = response.getBytes();

            fout.write(bytes);
            fout.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String readStatus(){
        String res="";
        try{
            File file=new File(Environment.getExternalStorageDirectory(), fileName);
            FileInputStream fin = new FileInputStream(file);

            int length = fin.available();

            byte [] buffer = new byte[length];
            fin.read(buffer);

            res =new String(buffer);

            fin.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
}

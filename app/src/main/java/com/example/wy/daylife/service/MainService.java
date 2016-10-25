package com.example.wy.daylife.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.example.wy.daylife.Interface.IWeiboActivity;
import com.example.wy.daylife.bean.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MainService extends Service implements Runnable {

    private static Queue<Task> tasks=new LinkedList<>();
    private static ArrayList<Activity> activities=new ArrayList<>();

    private boolean isRun;

    public MainService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void run() {
        while (isRun){
            if(!tasks.isEmpty()){
                Task task=tasks.poll();//执行完任务后把该任务移除队列
                if(null!=task){
                    doTask(task);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void doTask(Task task) {

        Message msg=handler.obtainMessage();
        msg.what=task.getTaskID();

        switch (task.getTaskID()){
            case Task.WB_LOGIN:
                msg.obj="登陆成功";
                break;
        }

    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Task.WB_LOGIN:
                    IWeiboActivity activity= (IWeiboActivity) getActivityByName("LoginActivity");
                    activity.refresh(msg.obj);
                    break;
            }
        }
    };

    //根据activity的name获取activity对象
    private Activity getActivityByName(String name){

        if(!activities.isEmpty()){
            for(Activity a:activities){
                if(a!=null){
                    if(a.getClass().getName().indexOf(name)>0){
                        return a;
                    }
                }
            }
        }
        return null;

    }

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void addTask(Task task){
        tasks.add(task);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isRun=true;
        Thread thread=new Thread(this);
        thread.start();
    }
}

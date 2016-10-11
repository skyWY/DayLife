package com.example.wy.daylife.bean;

import java.util.Map;

/**
 * Created by wy on 2016/10/11.
 */

public class Task {
    private int taskID;
    private Map<String, Object> params;

    public static final int WB_LOGIN=1;

    public Task(int taskID, Map<String, Object> params) {
        this.taskID = taskID;
        this.params = params;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}

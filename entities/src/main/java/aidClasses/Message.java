package aidClasses;


import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;

import java.io.Serializable;
import java.time.LocalTime;

public class Message implements Serializable {
    private static final long serialVersionUID = -8224097662914849956L;
    private String msg;
    private Object obj; // this object help us carry  an object from a different kinds
    private LocalTime time;




    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Message() {
    }

    public Message(String msg) {
        this.msg = msg;
        this.obj=null;
        this.time = LocalTime.now();
    }
    public Message(String msg, Object obj) {
        this.msg = msg;
        this.obj=obj;
        this.time = LocalTime.now();
    }

    public Message(String msg, Object obj, LocalTime time) {
        this.msg = msg;
        this.obj = obj;
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
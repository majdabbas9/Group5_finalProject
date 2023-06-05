package aidClasses;


import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

public class Message implements Serializable {
    private static final long serialVersionUID = -8224097662914849956L;
    private String msg;
    private Object obj; // this object help us carry  an object from a different kinds
    private Object  dataObject;
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
    public Message(String msg, Object obj,Object dataObject) {
        this.msg = msg;
        this.obj=obj;
        this.time = LocalTime.now();
        this.dataObject=dataObject;
    }

    public LocalTime getTime() {
        return time;
    }

    public Object getDataObject() {
        return dataObject;
    }

    public void setDataObject(Object dataObject) {
        this.dataObject = dataObject;
    }
}
package aidClasses;


import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = -8224097662914849956L;
    private String msg;
    private Object obj; // this object help us carry  an object from a different kinds

    // when we want to update a certain grade we need to send to the server msg which contain a grade number
    private int gradeNum;

    // when we want to update a certain grade we need to send to the server msg which contain a new grade number
    private int NewGrade;

    public String getMsg() {
        return msg;
    }
    public int getGradeNum(){return  gradeNum;}
    public int getNewGrade(){return  NewGrade;}
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
        this.gradeNum=0;
        this.NewGrade=0;
    }
    public Message(String msg, Object obj, int gradeNum, int newGrade) {
        this.msg = msg;
        this.obj=obj;
        this.gradeNum=gradeNum;
        this.NewGrade=newGrade;
    }

    public Message(String msg, Object obj) {
        this.msg = msg;
        this.obj = obj;
        this.gradeNum=0;
        this.NewGrade=0;
    }
}
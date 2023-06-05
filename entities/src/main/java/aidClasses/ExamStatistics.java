package aidClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExamStatistics {
    private List<Integer> allGrades;
    private Integer[] Histogram = new Integer[10];
    private double Average;
    private int Median;
    private String Exam_Id;
    private String Subject_Name;
    private String Course_Name;
    private String Teacher_Name;
    private int grade;


    public ExamStatistics(String exam_Id,String subject_Name,String course_Name,String teacher_Name){
        Exam_Id = exam_Id;
        Subject_Name = subject_Name;
        Course_Name = course_Name;
        Teacher_Name = teacher_Name;
        for (int i = 0 ; i < 10 ; ++i){
            Histogram[i] = 0;
        }
        allGrades = new ArrayList<>();
        Average = 0;
        Median = 0;
        grade = 0;
    }
    public ExamStatistics(){

    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public double getAverage() {
        return Average;
    }

    public int getMedian() {
        return Median;
    }

    public Integer[] getHistogram() {
        return Histogram;
    }

    public String getCourse_Name() {
        return Course_Name;
    }

    public String getExam_Id() {
        return Exam_Id;
    }

    public String getSubject_Name() {
        return Subject_Name;
    }

    public String getTeacher_Name() {
        return Teacher_Name;
    }

    public List<Integer> getAllGrades() {
        return allGrades;
    }

    public void setAverage() {
        if (allGrades.size() == 0){
            return;
        }
        Average = allGrades.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
    }

    public void setCourse_Name(String course_Name) {
        Course_Name = course_Name;
    }

    public void setExam_Id(String exam_Id) {
        Exam_Id = exam_Id;
    }

    public void setHistogram(Integer[] histogram) {
        Histogram = histogram;
    }

    public void setMedian() {
        if (allGrades.size() == 0){
            return;
        }
        Collections.sort(allGrades);
        Median = allGrades.get((int) (allGrades.size() / 2));
    }

    public void setSubject_Name(String subject_Name) {
        Subject_Name = subject_Name;
    }

    public void setTeacher_Name(String teacher_Name) {
        Teacher_Name = teacher_Name;
    }

    public void setAllGrades(List<Integer> allGrades) {
        this.allGrades = allGrades;
    }
}

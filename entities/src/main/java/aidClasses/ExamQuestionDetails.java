package aidClasses;

public class ExamQuestionDetails {
    private String _The_Question;
    private Integer _Points;

    public ExamQuestionDetails(String _the_question, Integer _points){
        this._Points = _points;
        this._The_Question = _the_question;
    }
    public Integer get_Points() {
        return _Points;
    }

    public void set_Points(Integer _Points) {
        this._Points = _Points;
    }

    public void set_The_Question(String _The_Question) {
        this._The_Question = _The_Question;
    }

    public String get_The_Question() {
        return _The_Question;
    }
}

package aidClasses.aidClassesForTeacher;

public class DisplayQuestion {
    private String theQuestion;
    private int points;

    public DisplayQuestion(String theQuestion, int points) {
        this.theQuestion = theQuestion;
        this.points = points;
    }

    public String getTheQuestion() {
        return theQuestion;
    }

    public void setTheQuestion(String theQuestion) {
        this.theQuestion = theQuestion;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

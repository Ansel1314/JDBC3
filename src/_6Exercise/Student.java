package _6Exercise;

/**
 * @author: Ansel
 * @date: 2021/5/4 0004 21:53
 * @description:封装的学生信息类
 */
public class Student {
    private int flowID;//流水号
    private int type;//考试类型
    private String IDCard;//身份证号
    private String examCard;//准考证号
    private String studentname;//学生姓名
    private String location;//所在城市
    private int grade;//考试成绩

    public Student() {
    }

    public Student(int flowID, int type, String IDCard, String examCard, String studentname, String location, int grade) {
        this.flowID = flowID;
        this.type = type;
        this.IDCard = IDCard;
        this.examCard = examCard;
        this.studentname = studentname;
        this.location = location;
        this.grade = grade;
    }

    public int getFlowID() {
        return flowID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getExamCard() {
        return examCard;
    }

    public void setExamCard(String examCard) {
        this.examCard = examCard;
    }

    public String getstudentname() {
        return studentname;
    }

    public void setstudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "flowID=" + flowID +
                ", type=" + type +
                ", IDCard='" + IDCard + '\'' +
                ", examCard='" + examCard + '\'' +
                ", studentname='" + studentname + '\'' +
                ", location='" + location + '\'' +
                ", grade=" + grade +
                '}';
    }
}

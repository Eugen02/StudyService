package com.zhoholiev.studyservice.StudyService.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Result_test")
public class ResultTest {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "test_rating")
    private int testRating;

    @Column(name = "current_attempt")
    private int currentAttempt;

    @Column(name = "start_time")
    private Date startDate;

    @Column(name = "end_time")
    private Date endDate;

    @Column(name="transit_time")
    private int transitTime;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;



    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test testToResult;

    @OneToMany(mappedBy = "resultTestInAnswer")
    private List<ResultAnswer> resultAnswers;

    public ResultTest() {
    }

    public ResultTest(int id) {
        this.id = id;
    }


    public int getTransitTime() {
        return transitTime;
    }

    public void setTransitTime(int transitTime) {
        this.transitTime = transitTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getCurrentAttempt() {
        return currentAttempt;
    }

    public void setCurrentAttempt(int currentAttempt) {
        this.currentAttempt = currentAttempt;
    }

    public int getTestRating() {
        return testRating;
    }

    public void setTestRating(int testRating) {
        this.testRating = testRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

//    public Test getTestToResult() {
//        return testToResult;
//    }
//
//    public void setTestToResult(Test testToResult) {
//        this.testToResult = testToResult;
//    }


    public Test getTestToResult() {
        return testToResult;
    }

    public void setTestToResult(Test testToResult) {
        this.testToResult = testToResult;
    }

    public List<ResultAnswer> getResultAnswers() {
        return resultAnswers;
    }

    public void setResultAnswers(List<ResultAnswer> resultAnswers) {
        this.resultAnswers = resultAnswers;
    }


}

package com.zhoholiev.studyservice.StudyService.models;

import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Test")
public class Test {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name="number_of_quest")
    private int numberOfQuest;

    @Column(name="rating")
    private int rating;

    @Column(name="attempt")
    private int attempt;

    @Column(name="time")
    private int time;

    @Column(name = "last_updated")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date testComplete;

    @Column(name="start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name="end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "number_of_quest_in_test")
    private int numberOfQuestInTest;

    @Column(name = "identificator")
    private int identificator;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course courseTest;

    @OneToMany(mappedBy = "test")
    private List<Quest> quests;

    @ManyToOne
    @JoinColumn(name = "course_block_id", referencedColumnName = "id")
    private CourseBlock testToCourseBlock;


    @OneToMany(mappedBy = "testToResult")
    private List<ResultTest> resultTest;

//    @OneToOne(mappedBy = "testToResult")
//    private ResultTest resultTest;

    public Test() {
    }

    public Test(String name, int numberOfQuest, int rating, int attempt,
                int time) {
        this.name = name;
        this.numberOfQuest = numberOfQuest;
        this.rating = rating;
        this.attempt = attempt;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfQuest() {
        return numberOfQuest;
    }

    public void setNumberOfQuest(int numberOfQuest) {
        this.numberOfQuest = numberOfQuest;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Date getTestComplete() {
        return testComplete;
    }

    public void setTestComplete(Date testComplete) {
        this.testComplete = testComplete;
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

    public Course getCourseTest() {
        return courseTest;
    }

    public void setCourseTest(Course courseTest) {
        this.courseTest = courseTest;
    }

    public CourseBlock getTestToCourseBlock() {
        return testToCourseBlock;
    }

    public void setTestToCourseBlock(CourseBlock testToCourseBlock) {
        this.testToCourseBlock = testToCourseBlock;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    public int getNumberOfQuestInTest() {
        return numberOfQuestInTest;
    }

    public void setNumberOfQuestInTest(int numberOfQuestInTest) {
        this.numberOfQuestInTest = numberOfQuestInTest;
    }

    public int getIdentificator() {
        return identificator;
    }

    public void setIdentificator(int identificator) {
        this.identificator = identificator;
    }

    //    public ResultTest getResultTest() {
//        return resultTest;
//    }
//
//    public void setResultTest(ResultTest resultTest) {
//        this.resultTest = resultTest;
//    }


    public List<ResultTest> getResultTest() {
        return resultTest;
    }

    public void setResultTest(List<ResultTest> resultTest) {
        this.resultTest = resultTest;
    }
}

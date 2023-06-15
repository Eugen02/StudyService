package com.zhoholiev.studyservice.StudyService.dto;

import com.zhoholiev.studyservice.StudyService.models.Course;

public class TestDTO {
    private String name;

    private int numberOfQuest;

    private int rating;

    private int attempt;

    private int time;


    private Course courseTest;


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

    public Course getCourseTest() {
        return courseTest;
    }

    public void setCourseTest(Course courseTest) {
        this.courseTest = courseTest;
    }
}

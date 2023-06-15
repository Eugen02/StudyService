package com.zhoholiev.studyservice.StudyService.util;

public class ResultTestNotExist {
    private int currentAttempt;
    private String testRating;

    public ResultTestNotExist(int currentAttempt, String testRating) {
        this.currentAttempt = currentAttempt;
        this.testRating = testRating;
    }

    public int getCurrentAttempt() {
        return currentAttempt;
    }

    public void setCurrentAttempt(int currentAttempt) {
        this.currentAttempt = currentAttempt;
    }

    public String getTestRating() {
        return testRating;
    }

    public void setTestRating(String testRating) {
        this.testRating = testRating;
    }
}

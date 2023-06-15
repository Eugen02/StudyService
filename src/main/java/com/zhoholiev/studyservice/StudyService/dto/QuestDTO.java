package com.zhoholiev.studyservice.StudyService.dto;

import com.zhoholiev.studyservice.StudyService.models.Answer;
import com.zhoholiev.studyservice.StudyService.models.Test;

import java.util.List;

public class QuestDTO {
    private String description;

    private Test test;

    private List<Answer> answers;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}



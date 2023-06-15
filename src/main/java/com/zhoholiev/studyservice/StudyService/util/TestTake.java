package com.zhoholiev.studyservice.StudyService.util;

import com.zhoholiev.studyservice.StudyService.models.*;

import java.util.ArrayList;
import java.util.List;

public class TestTake {

    Test test;
    ResultTest resultTest;
    List<ResultAnswer> resultAnswerList;
    List<Answer> answerList;
    Student student;

    String time;

    int correctAnswersAll;
    List<CompleteTest> completeTest = new ArrayList<>();

    private  CompleteTest newCompleteTestTT = new CompleteTest();

    public TestTake(Test test, ResultTest resultTest, List<ResultAnswer> resultAnswerList) {
        this.test = test;
        this.resultTest = resultTest;
        this.resultAnswerList = resultAnswerList;
    }

    public TestTake(Test test, ResultTest resultTest, List<ResultAnswer> resultAnswerList, List<Answer> answerList, Student student) {
        this.test = test;
        this.resultTest = resultTest;
        this.resultAnswerList = resultAnswerList;
        this.answerList = answerList;
        this.student = student;
    }

    public TestTake(Test test, ResultTest resultTest, List<ResultAnswer> resultAnswerList, List<Answer> answerList) {
        this.test = test;
        this.resultTest = resultTest;
        this.resultAnswerList = resultAnswerList;
        this.answerList = answerList;
    }

    public CompleteTest getNewCompleteTestTT() {
        return newCompleteTestTT;
    }

    public int getCorrectAnswersAll() {
        return correctAnswersAll;
    }

    public void setCorrectAnswersAll(int correctAnswersAll) {
        this.correctAnswersAll = correctAnswersAll;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNewCompleteTestTT(CompleteTest newCompleteTest) {
        this.newCompleteTestTT = newCompleteTest;
    }

    public List<CompleteTest> getCompleteTest() {
        return completeTest;
    }

    public void setCompleteTest(List<CompleteTest> completeTest) {
        this.completeTest = completeTest;
    }

    public List<CompleteTest> insertQuests(List<Quest> quest){
        List<CompleteTest> completeTest1 = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < quest.size(); i++){
            completeTest1.add(new CompleteTest(
                    quest.get(i),
                    quest.get(i).getAnswers().get(counter++),
                    quest.get(i).getAnswers().get(counter++),
                    quest.get(i).getAnswers().get(counter++),
                    quest.get(i).getAnswers().get(counter)

            ));
            counter = 0;
        }
        return completeTest1;
    }

    public TestTake() {
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public ResultTest getResultTest() {
        return resultTest;
    }

    public void setResultTest(ResultTest resultTest) {
        this.resultTest = resultTest;
    }

    public List<ResultAnswer> getResultAnswerList() {
        return resultAnswerList;
    }

    public void setResultAnswerList(List<ResultAnswer> resultAnswerList) {
        this.resultAnswerList = resultAnswerList;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "TestTake{" +
                "completeTest=" + completeTest +
                '}';
    }
}



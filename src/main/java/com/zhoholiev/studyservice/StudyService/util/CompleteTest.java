package com.zhoholiev.studyservice.StudyService.util;

import com.zhoholiev.studyservice.StudyService.models.Answer;
import com.zhoholiev.studyservice.StudyService.models.Quest;
import com.zhoholiev.studyservice.StudyService.models.ResultAnswer;
import com.zhoholiev.studyservice.StudyService.models.ResultTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompleteTest {
    private Quest quest;
    private Answer answerFalse1;
    private Answer answerFalse2;
    private Answer answerFalse3;
    private Answer answerTrue;

    private List<Answer> answerList = new ArrayList<>(4);

    private int resultAnswerId = 0;


    public CompleteTest() {
    }

    public CompleteTest(Quest quest, Answer answerTrue, Answer answerFalse1, Answer answerFalse2, Answer answerFalse3) {
        this.quest = quest;
        answerList.add(answerFalse1);
        answerList.add(answerFalse2);
        answerList.add(answerFalse3);
        answerList.add(answerTrue);
        Collections.shuffle(answerList);

        this.answerFalse1 = answerList.get(0);
        this.answerFalse2 = answerList.get(1);
        this.answerFalse3 = answerList.get(2);
        this.answerTrue = answerList.get(3);
    }

    public CompleteTest(Quest quest, Answer answerFalse1, Answer answerFalse2, Answer answerFalse3, Answer answerTrue, ResultAnswer resultAnswer, ResultTest resultTest) {
        this.quest = quest;
        this.answerFalse1 = answerFalse1;
        this.answerFalse2 = answerFalse2;
        this.answerFalse3 = answerFalse3;
        this.answerTrue = answerTrue;
    }


    public int getResultAnswerId() {
        return resultAnswerId;
    }

    public void setResultAnswerId(int resultAnswerId) {
        this.resultAnswerId = resultAnswerId;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public Answer getAnswerFalse1() {
        return answerFalse1;
    }

    public void setAnswerFalse1(Answer answerFalse1) {
        this.answerFalse1 = answerFalse1;
    }

    public Answer getAnswerFalse2() {
        return answerFalse2;
    }

    public void setAnswerFalse2(Answer answerFalse2) {
        this.answerFalse2 = answerFalse2;
    }

    public Answer getAnswerFalse3() {
        return answerFalse3;
    }

    public void setAnswerFalse3(Answer answerFalse3) {
        this.answerFalse3 = answerFalse3;
    }

    public Answer getAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(Answer answerTrue) {
        this.answerTrue = answerTrue;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }


    @Override
    public String toString() {
        return "CompleteTest{" +
                "quest=" + quest +
                ", answerFalse1=" + answerFalse1 +
                ", answerFalse2=" + answerFalse2 +
                ", answerFalse3=" + answerFalse3 +
                ", answerTrue=" + answerTrue +
                ", resultAnswer" + resultAnswerId +
                '}';
    }
}

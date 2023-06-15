package com.zhoholiev.studyservice.StudyService.models;

import javax.persistence.*;


@Entity
@Table(name = "Result_answer")
public class ResultAnswer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "answer_value_id")
    private int answerValueId;

    @Column(name = "corect_answer")
    private int corectAnswer;

    @Column(name = "quest_id")
    private int quest_id;

    @ManyToOne
    @JoinColumn(name = "result_test_id", referencedColumnName = "id")
    private ResultTest resultTestInAnswer;
    @ManyToOne
    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    private Answer answerIn;

//    @OneToOne
//    @JoinColumn(name = "answer_id", referencedColumnName = "id")
//    private Answer answerIn;


    public ResultAnswer() {
    }

    public ResultAnswer(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuest_id() {
        return quest_id;
    }

    public void setQuest_id(int quest_id) {
        this.quest_id = quest_id;
    }

    public ResultTest getResultTestInAnswer() {
        return resultTestInAnswer;
    }

    public void setResultTestInAnswer(ResultTest resultTestInAnswer) {
        this.resultTestInAnswer = resultTestInAnswer;
    }

    public Answer getAnswerIn() {
        return answerIn;
    }

    public void setAnswerIn(Answer answerIn) {
        this.answerIn = answerIn;
    }

    public int getAnswerValueId() {
        return answerValueId;
    }

    public void setAnswerValueId(int answerValueId) {
        this.answerValueId = answerValueId;
    }


    public int getCorectAnswer() {
        return corectAnswer;
    }

    public void setCorectAnswer(int corectAnswer) {
        this.corectAnswer = corectAnswer;
    }

    @Override
    public String toString() {
        return "ResultAnswer{" +
                "id=" + id +
                ", answerValueId=" + answerValueId +
                ", corectAnswer=" + corectAnswer +
                '}';
    }
}

package com.zhoholiev.studyservice.StudyService.models;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "Answer")
public class Answer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "answer")
    private String answer;

    @Column(name = "is_true")
    private int isTrue;

    @ManyToOne
    @JoinColumn(name = "quest_id", referencedColumnName = "id")
    private Quest quest;

//    @OneToOne(mappedBy = "answerIn")
//    private ResultAnswer resultAnswer;

    @OneToMany(mappedBy = "answerIn")
    private List<ResultAnswer> resultAnswer;

    public Answer() {
    }

    public Answer(String answer, int isTrue) {
        this.answer = answer;
        this.isTrue = isTrue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(int isTrue) {
        this.isTrue = isTrue;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", isTrue=" + isTrue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer1 = (Answer) o;

        if (id != answer1.id) return false;
        if (isTrue != answer1.isTrue) return false;
        return answer != null ? answer.equals(answer1.answer) : answer1.answer == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + isTrue;
        return result;
    }
//
//    public ResultAnswer getResultAnswer() {
//        return resultAnswer;
//    }
//
//    public void setResultAnswer(ResultAnswer resultAnswer) {
//        this.resultAnswer = resultAnswer;
//    }


    public List<ResultAnswer> getResultAnswer() {
        return resultAnswer;
    }

    public void setResultAnswer(List<ResultAnswer> resultAnswer) {
        this.resultAnswer = resultAnswer;
    }
}

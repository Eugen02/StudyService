package com.zhoholiev.studyservice.StudyService.dto;

import com.zhoholiev.studyservice.StudyService.models.Answer;
import com.zhoholiev.studyservice.StudyService.models.StudentGroup;
import com.zhoholiev.studyservice.StudyService.models.User;


import java.util.List;

public class StudentDTO {

    private String course;

    private StudentGroup studentGroup;

    private User userStudent;

    private List<Answer> answers;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public User getUserStudent() {
        return userStudent;
    }

    public void setUserStudent(User userStudent) {
        this.userStudent = userStudent;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}

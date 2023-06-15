package com.zhoholiev.studyservice.StudyService.dto;

import com.zhoholiev.studyservice.StudyService.models.Course;
import com.zhoholiev.studyservice.StudyService.models.User;

import java.util.List;

public class TeacherDTO {

    private String position;

    private User userTeacher;

    private List<Course> courses;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public User getUserTeacher() {
        return userTeacher;
    }

    public void setUserTeacher(User userTeacher) {
        this.userTeacher = userTeacher;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

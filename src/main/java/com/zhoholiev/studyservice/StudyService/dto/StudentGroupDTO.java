package com.zhoholiev.studyservice.StudyService.dto;

import com.zhoholiev.studyservice.StudyService.models.Course;
import com.zhoholiev.studyservice.StudyService.models.Student;

import java.util.List;

public class StudentGroupDTO {

    private String name;

    private List<Student> students;

    private List<Course> courses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

package com.zhoholiev.studyservice.StudyService.dto;

import com.zhoholiev.studyservice.StudyService.models.LectureMaterial;
import com.zhoholiev.studyservice.StudyService.models.StudentGroup;
import com.zhoholiev.studyservice.StudyService.models.Teacher;
import com.zhoholiev.studyservice.StudyService.models.Test;

import java.util.List;

public class CourseDTO {

    private String name;
    private int courseRating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourseRating() {
        return courseRating;
    }

    public void setCourseRating(int courseRating) {
        this.courseRating = courseRating;
    }

    private Teacher teacher;

    private List<StudentGroup> studentGroups;

    private List<LectureMaterial> lectureMaterials;

    private List<Test> tests;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<LectureMaterial> getLectureMaterials() {
        return lectureMaterials;
    }

    public void setLectureMaterials(List<LectureMaterial> lectureMaterials) {
        this.lectureMaterials = lectureMaterials;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
}

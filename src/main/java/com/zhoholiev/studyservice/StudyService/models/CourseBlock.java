package com.zhoholiev.studyservice.StudyService.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Course_block")
public class CourseBlock {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_block")
    private String nameBlock;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "lectureMaterialToCourseBlock")
    private List<LectureMaterial> lectureMaterials;

    @OneToMany(mappedBy = "testToCourseBlock")
    private List<Test> tests;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course courseCourseBlock;

    public CourseBlock() {}

    public CourseBlock(String nameBlock) {
        this.nameBlock = nameBlock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameBlock() {
        return nameBlock;
    }

    public void setNameBlock(String nameBlock) {
        this.nameBlock = nameBlock;
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

    public Course getCourseCourseBlock() {
        return courseCourseBlock;
    }

    public void setCourseCourseBlock(Course courseCourseBlock) {
        this.courseCourseBlock = courseCourseBlock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

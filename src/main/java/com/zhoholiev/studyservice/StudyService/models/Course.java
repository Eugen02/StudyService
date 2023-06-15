package com.zhoholiev.studyservice.StudyService.models;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "Course")
public class Course {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "course_rating")
    private int courseRating;

    @Column(name="description")
    private String description;

    @Column(name = "link_page")
    private String linkPage;

    @ManyToOne()
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @ManyToMany(mappedBy = "courses")
    private List<StudentGroup> studentGroups;

    @OneToMany(mappedBy = "course")
    private List<LectureMaterial> lectureMaterials;

    @OneToMany(mappedBy = "courseCourseBlock")
    private List<CourseBlock> courseBlocks;

    @OneToMany(mappedBy = "courseTest")
    private List<Test> tests;

    public Course() {
    }

    public Course(String name, int courseRating, String description) {
        this.name = name;
        this.courseRating = courseRating;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkPage() {
        return linkPage;
    }

    public void setLinkPage(String linkPage) {
        this.linkPage = linkPage;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public List<CourseBlock> getCourseBlocks() {
        return courseBlocks;
    }

    public void setCourseBlocks(List<CourseBlock> courseBlocks) {
        this.courseBlocks = courseBlocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        if (courseRating != course.courseRating) return false;
        return name != null ? name.equals(course.name) : course.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + courseRating;
        return result;
    }
}

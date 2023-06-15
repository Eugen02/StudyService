package com.zhoholiev.studyservice.StudyService.models;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "Student")
public class Student {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_group_id", referencedColumnName = "id")
    private StudentGroup studentGroup;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userStudent;

    @OneToMany(mappedBy = "student")
    private List<ResultTest> resultTests;

    @OneToMany(mappedBy = "studentInResult")
    private List<ResultLectureMaterial> resultLectureMaterials;


    public Student(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserStudent() {
        return userStudent;
    }

    public void setUserStudent(User userStudent) {
        this.userStudent = userStudent;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public List<ResultTest> getResultTests() {
        return resultTests;
    }

    public void setResultTests(List<ResultTest> resultTests) {
        this.resultTests = resultTests;
    }

    public List<ResultLectureMaterial> getResultLectureMaterials() {
        return resultLectureMaterials;
    }

    public void setResultLectureMaterials(List<ResultLectureMaterial> resultLectureMaterials) {
        this.resultLectureMaterials = resultLectureMaterials;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentGroup=" + studentGroup.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return id == student.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

package com.zhoholiev.studyservice.StudyService.models;

import javax.persistence.*;

@Entity
@Table(name = "Result_lecture_material")
public class ResultLectureMaterial {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "checkd")
    private int checkd;

    @ManyToOne
    @JoinColumn(name = "lecture_material_id", referencedColumnName = "id")
    private LectureMaterial lectureMaterialInResult;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student studentInResult;


    public ResultLectureMaterial() {
    }

    public ResultLectureMaterial(int checkd) {
        this.checkd = checkd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCheckd() {
        return checkd;
    }

    public void setCheckd(int checkd) {
        this.checkd = checkd;
    }

    public LectureMaterial getLectureMaterialInResult() {
        return lectureMaterialInResult;
    }

    public void setLectureMaterialInResult(LectureMaterial lectureMaterialInResult) {
        this.lectureMaterialInResult = lectureMaterialInResult;
    }

    public Student getStudentInResult() {
        return studentInResult;
    }

    public void setStudentInResult(Student studentInResult) {
        this.studentInResult = studentInResult;
    }
}

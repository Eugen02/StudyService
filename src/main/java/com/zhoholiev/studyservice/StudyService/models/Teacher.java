package com.zhoholiev.studyservice.StudyService.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "Teacher")
public class Teacher {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "position")
    private String position;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userTeacher;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

    public Teacher() {

    }

    public Teacher(String position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public User getUser() {
        return userTeacher;
    }

    public void setUser(User userTeacher) {
        this.userTeacher = userTeacher;
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

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", position='" + position + '\'' +
                ", userTeacher=" + userTeacher +
                ", courses=" + courses +
                '}';
    }
}

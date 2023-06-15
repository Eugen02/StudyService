package com.zhoholiev.studyservice.StudyService.models;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "Student_group")
public class StudentGroup {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    @Column(name = "course_number")
    private int courseNumber;

    @Column(name = "student_number")
    private int studentNumber;

    @OneToMany(mappedBy = "studentGroup")
    private List<Student> students;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Student_group_has_course",
    joinColumns = @JoinColumn(name = "student_group_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id") )
    private List<Course> courses;

    public StudentGroup() {
    }

    public StudentGroup(String name, int courseNumber) {
        this.name = name;
        this.courseNumber = courseNumber;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }


    @Override
    public String toString() {
        return "StudentGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courseNumber=" + courseNumber +
                ", studentNumber=" + studentNumber +
                ", students=" + students +
                ", courses=" + courses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentGroup that = (StudentGroup) o;

        if (id != that.id) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}

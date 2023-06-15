package com.zhoholiev.studyservice.StudyService.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    @NotBlank(message = " Enter Name")
    @Length(min = 2, max = 300, message = " The name must be longer than 2 characters")
    private String firstName;
    @Column(name = "last_name")
    @NotBlank(message = " Enter the last name.")
    @Length(min = 2, max = 300, message = " The middle last name be longer than 2 characters")
    private String lastName;
    @Column(name = "middle_name")
    @NotBlank(message = " Enter the middle name.")
    @Length(min = 2, max = 300, message = " The middle must name be longer than 2 characters")
    private String middleName;
    @Column(name = "email")
    @Pattern(regexp = "[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = " E-mail must be valid")
    @NotBlank(message = " Enter Email")
    private String email;
    @Column(name = "user_password")
    @NotBlank(message = " Enter Password")
    @Length(min = 5, max = 300, message = " Password must be longer than 5 characters")
    private String userPassword;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @OneToOne(mappedBy = "userTeacher")
    private Teacher teacher;

    @OneToOne(mappedBy = "userStudent")
    private Student student;

    public User(){

    }

    public User(String firstName, String lastName, String middleName, String email, String userPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.userPassword = userPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", email='" + email + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", role=" + role.getName() +
                ", student=" +
                '}';
    }
}

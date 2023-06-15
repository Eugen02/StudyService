package com.zhoholiev.studyservice.StudyService.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Lecture_material")
public class LectureMaterial {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "link")
    private String link;

    @Column(name = "name_video")
    private String nameVideo;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "course_block_id", referencedColumnName = "id")
    private CourseBlock lectureMaterialToCourseBlock;

    @OneToMany(mappedBy = "lectureMaterialInResult")
    private List<ResultLectureMaterial> resultLectureMaterials;
    public LectureMaterial() {
    }

    public LectureMaterial(String name, String description, String link) {
        this.name = name;
        this.description = description;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameVideo() {
        return nameVideo;
    }

    public void setNameVideo(String nameVideo) {
        this.nameVideo = nameVideo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<ResultLectureMaterial> getResultLectureMaterials() {
        return resultLectureMaterials;
    }

    public void setResultLectureMaterials(List<ResultLectureMaterial> resultLectureMaterials) {
        this.resultLectureMaterials = resultLectureMaterials;
    }

    public CourseBlock getLectureMaterialToCourseBlock() {
        return lectureMaterialToCourseBlock;
    }

    public void setLectureMaterialToCourseBlock(CourseBlock lectureMaterialToCourseBlock) {
        this.lectureMaterialToCourseBlock = lectureMaterialToCourseBlock;
    }
}

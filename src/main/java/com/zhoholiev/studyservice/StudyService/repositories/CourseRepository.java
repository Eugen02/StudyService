package com.zhoholiev.studyservice.StudyService.repositories;

import com.zhoholiev.studyservice.StudyService.models.Course;
import com.zhoholiev.studyservice.StudyService.models.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query(value = "select b from Course b where b.teacher.id = :id")
    List<Course> getCourse(@Param("id") int id);

    @Query(value = "select b from Course b where b.id = :id")
    Course getCourseById(@Param("id") int id);

    @Query(value = "select b from Course b join b.studentGroups sg where  sg.id =:id")
    List<Course> getCourseForStudent(@Param("id") int id);


}

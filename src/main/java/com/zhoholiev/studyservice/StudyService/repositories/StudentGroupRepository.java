package com.zhoholiev.studyservice.StudyService.repositories;

import com.zhoholiev.studyservice.StudyService.models.Course;
import com.zhoholiev.studyservice.StudyService.models.ResultTest;
import com.zhoholiev.studyservice.StudyService.models.Student;
import com.zhoholiev.studyservice.StudyService.models.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Integer> {

//    @Query(value = "select b from StudentGroup b where b.students = :id")
//    List<Course> getCourse(@Param("id") int id);
}

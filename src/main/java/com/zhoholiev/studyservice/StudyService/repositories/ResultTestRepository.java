package com.zhoholiev.studyservice.StudyService.repositories;

import com.zhoholiev.studyservice.StudyService.models.ResultTest;
import com.zhoholiev.studyservice.StudyService.models.Student;
import com.zhoholiev.studyservice.StudyService.models.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultTestRepository extends JpaRepository<ResultTest, Integer> {

    ResultTest findByStudentAndTestToResult(Student student, Test test);
    List<ResultTest> findByStudent(Student student);
}

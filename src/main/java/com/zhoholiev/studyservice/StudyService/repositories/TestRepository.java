package com.zhoholiev.studyservice.StudyService.repositories;

import com.zhoholiev.studyservice.StudyService.models.Course;
import com.zhoholiev.studyservice.StudyService.models.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test,Integer> {

    Test findByName(String name);

    Test findByIdentificator(int id);

    List<Test> findByCourseTest(Course course);
}

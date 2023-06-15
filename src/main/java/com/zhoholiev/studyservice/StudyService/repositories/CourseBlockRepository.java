package com.zhoholiev.studyservice.StudyService.repositories;

import com.zhoholiev.studyservice.StudyService.models.CourseBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseBlockRepository extends JpaRepository<CourseBlock, Integer> {
}

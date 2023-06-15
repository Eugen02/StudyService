package com.zhoholiev.studyservice.StudyService.repositories;

import com.zhoholiev.studyservice.StudyService.models.ResultAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultAnswerRepository extends JpaRepository<ResultAnswer,Integer> {
}

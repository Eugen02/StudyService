package com.zhoholiev.studyservice.StudyService.repositories;

import com.zhoholiev.studyservice.StudyService.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}

package com.zhoholiev.studyservice.StudyService.repositories;

import com.zhoholiev.studyservice.StudyService.models.LectureMaterial;
import com.zhoholiev.studyservice.StudyService.models.ResultLectureMaterial;
import com.zhoholiev.studyservice.StudyService.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultLectureMaterialRepository extends JpaRepository<ResultLectureMaterial, Integer> {

    Optional<ResultLectureMaterial> findByStudentInResultAndLectureMaterialInResult(Student student, LectureMaterial lectureMaterial);

}

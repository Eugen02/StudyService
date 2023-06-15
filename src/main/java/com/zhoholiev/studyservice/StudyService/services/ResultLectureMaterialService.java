package com.zhoholiev.studyservice.StudyService.services;

import com.zhoholiev.studyservice.StudyService.models.LectureMaterial;
import com.zhoholiev.studyservice.StudyService.models.ResultLectureMaterial;
import com.zhoholiev.studyservice.StudyService.models.Student;
import com.zhoholiev.studyservice.StudyService.repositories.ResultLectureMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResultLectureMaterialService {

    ResultLectureMaterialRepository resultLectureMaterialRepository;

    @Autowired
    public ResultLectureMaterialService(ResultLectureMaterialRepository resultLectureMaterialRepository) {
        this.resultLectureMaterialRepository = resultLectureMaterialRepository;
    }


    public List<ResultLectureMaterial> showAll() {
        return resultLectureMaterialRepository.findAll();
    }

    public ResultLectureMaterial findOne(int id) {
        return resultLectureMaterialRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(ResultLectureMaterial user) {
        resultLectureMaterialRepository.save(user);
    }

    @Transactional
    public void update(int id, ResultLectureMaterial updatedUser) {
        updatedUser.setId(id);
        resultLectureMaterialRepository.save(updatedUser);
    }

    @Transactional
    public void delete(int id) {
        resultLectureMaterialRepository.deleteById(id);
    }

    @Transactional
    public ResultLectureMaterial findByStudentAndLecture(Student student, LectureMaterial lectureMaterial){
        return resultLectureMaterialRepository.findByStudentInResultAndLectureMaterialInResult(student, lectureMaterial).orElse(null);
    }

}

package com.zhoholiev.studyservice.StudyService.services;

import com.zhoholiev.studyservice.StudyService.models.Answer;
import com.zhoholiev.studyservice.StudyService.models.ResultTest;
import com.zhoholiev.studyservice.StudyService.models.Student;
import com.zhoholiev.studyservice.StudyService.models.Test;
import com.zhoholiev.studyservice.StudyService.repositories.ResultTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResultTestService {

    ResultTestRepository resultTestRepository;

    @Autowired
    public ResultTestService(ResultTestRepository resultTestRepository) {
        this.resultTestRepository = resultTestRepository;
    }

    public List<ResultTest> showAll() {
        return resultTestRepository.findAll();
    }

    public ResultTest findOne(int id) {
        return resultTestRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(ResultTest user) {
        resultTestRepository.save(user);
    }

    @Transactional
    public void update(int id, ResultTest updatedUser) {
        updatedUser.setId(id);
        resultTestRepository.save(updatedUser);
    }

    @Transactional
    public void delete(int id) {
        resultTestRepository.deleteById(id);
    }

    @Transactional
    public ResultTest findByStudentAndTest(Student student, Test test) {
        return resultTestRepository.findByStudentAndTestToResult(student, test);
    }

    @Transactional
    public List<ResultTest> findByStudent(Student student) {
        return resultTestRepository.findByStudent(student);
    }

}

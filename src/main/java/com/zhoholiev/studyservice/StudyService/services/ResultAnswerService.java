package com.zhoholiev.studyservice.StudyService.services;

import com.zhoholiev.studyservice.StudyService.models.ResultAnswer;
import com.zhoholiev.studyservice.StudyService.models.ResultTest;
import com.zhoholiev.studyservice.StudyService.repositories.ResultAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResultAnswerService {
    ResultAnswerRepository resultAnswerRepository;

    @Autowired
    public ResultAnswerService(ResultAnswerRepository resultAnswerRepository) {
        this.resultAnswerRepository = resultAnswerRepository;
    }

    public List<ResultAnswer> showAll(){
        return resultAnswerRepository.findAll();
    }

    public  ResultAnswer findOne(int id){
        return resultAnswerRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(ResultAnswer user){
        resultAnswerRepository.save(user);
    }

    @Transactional
    public void update(int id, ResultAnswer updatedUser){
        updatedUser.setId(id);
        resultAnswerRepository.save(updatedUser);
    }

    @Transactional
    public void delete(int id){
        resultAnswerRepository.deleteById(id);
    }

}

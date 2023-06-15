package com.zhoholiev.studyservice.StudyService.services;

import com.zhoholiev.studyservice.StudyService.models.Answer;
import com.zhoholiev.studyservice.StudyService.models.User;
import com.zhoholiev.studyservice.StudyService.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AnswerService {

    AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> showAll(){
        return answerRepository.findAll();
    }

    public  Answer findOne(int id){
        return answerRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Answer user){
        answerRepository.save(user);
    }

    @Transactional
    public void update(int id, Answer updatedUser){
        updatedUser.setId(id);
        answerRepository.save(updatedUser);
    }

    @Transactional
    public void delete(int id){
        answerRepository.deleteById(id);
    }

}

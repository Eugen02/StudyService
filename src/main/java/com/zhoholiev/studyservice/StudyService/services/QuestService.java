package com.zhoholiev.studyservice.StudyService.services;

import com.zhoholiev.studyservice.StudyService.models.Quest;
import com.zhoholiev.studyservice.StudyService.models.Test;
import com.zhoholiev.studyservice.StudyService.repositories.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class QuestService {

    QuestRepository questRepository;

    @Autowired
    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    public List<Quest> showAll(){
        return questRepository.findAll();
    }

    public  Quest findOne(int id){
        return questRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Quest test){
        questRepository.save(test);
    }

    @Transactional
    public void update(int id, Quest updatedTest){
        updatedTest.setId(id);
        questRepository.save(updatedTest);
    }

    @Transactional
    public void delete(int id){
        questRepository.deleteById(id);
    }
}

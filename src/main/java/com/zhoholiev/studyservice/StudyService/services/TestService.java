package com.zhoholiev.studyservice.StudyService.services;

import com.zhoholiev.studyservice.StudyService.models.Course;
import com.zhoholiev.studyservice.StudyService.models.Test;
import com.zhoholiev.studyservice.StudyService.models.User;
import com.zhoholiev.studyservice.StudyService.repositories.TestRepository;
import com.zhoholiev.studyservice.StudyService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TestService {

    TestRepository testRepository;

    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<Test> showAll(){
        return testRepository.findAll();
    }

    public  Test findOne(int id){
        return testRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Test test){
        test.setStartDate(new Date());
        testRepository.save(test);
    }

    @Transactional
    public void update(int id, Test updatedTest){
        updatedTest.setId(id);
        testRepository.save(updatedTest);
    }

    @Transactional
    public void delete(int id){
        testRepository.deleteById(id);
    }

    @Transactional
    public Test findByNameTest(String name){
       return testRepository.findByName(name);
    }

    @Transactional
    public Test findByfindByIdentificator(int i){
       return testRepository.findByIdentificator(i);
    }

    @Transactional
    public List<Test> findByCourse(Course course){
       return testRepository.findByCourseTest(course);
    }
}

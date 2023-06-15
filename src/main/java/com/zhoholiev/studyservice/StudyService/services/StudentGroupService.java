package com.zhoholiev.studyservice.StudyService.services;

import com.zhoholiev.studyservice.StudyService.models.StudentGroup;
import com.zhoholiev.studyservice.StudyService.repositories.StudentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentGroupService(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }


    public List<StudentGroup> showAll(){
        return studentGroupRepository.findAll();
    }

    public StudentGroup findOne(int id){
        return studentGroupRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(StudentGroup user){
        studentGroupRepository.save(user);
    }

    @Transactional
    public void update(int id, StudentGroup updatedUser){
        updatedUser.setId(id);
        studentGroupRepository.save(updatedUser);
    }

    @Transactional
    public void delete(int id){
        studentGroupRepository.deleteById(id);
    }

}

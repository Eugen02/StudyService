package com.zhoholiev.studyservice.StudyService.services;

import com.zhoholiev.studyservice.StudyService.models.Teacher;
import com.zhoholiev.studyservice.StudyService.models.User;
import com.zhoholiev.studyservice.StudyService.repositories.TeacherRepository;
import com.zhoholiev.studyservice.StudyService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> showAll(){
        return teacherRepository.findAll();
    }

    public  Teacher findOne(int id){
        return teacherRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Teacher teacher){
        teacherRepository.save(teacher);
    }

    @Transactional
    public void update(int id, Teacher updatedUser){
        updatedUser.setId(id);
        teacherRepository.save(updatedUser);
    }

    @Transactional
    public void delete(int id){
        teacherRepository.deleteById(id);
    }



}

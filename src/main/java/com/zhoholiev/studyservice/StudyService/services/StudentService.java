package com.zhoholiev.studyservice.StudyService.services;

import com.zhoholiev.studyservice.StudyService.models.Student;
import com.zhoholiev.studyservice.StudyService.models.Teacher;
import com.zhoholiev.studyservice.StudyService.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> showAll(){
        return studentRepository.findAll();
    }

    public  Student findOne(int id){
        return studentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Student student){
        studentRepository.save(student);
    }

    @Transactional
    public void update(int id, Student updatedUser){
        updatedUser.setId(id);
        studentRepository.save(updatedUser);
    }

    @Transactional
    public void delete(int id){
        studentRepository.deleteById(id);
    }



}

package com.zhoholiev.studyservice.StudyService.services;

import com.zhoholiev.studyservice.StudyService.models.Course;
import com.zhoholiev.studyservice.StudyService.models.ResultTest;
import com.zhoholiev.studyservice.StudyService.models.Role;
import com.zhoholiev.studyservice.StudyService.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CourseService {

    CourseRepository courseRepository;

    ResultTestService resultTestService;

    StudentService studentService;

    TestService testService;

    @Autowired
    public CourseService(CourseRepository courseRepository, ResultTestService resultTestService,
                         StudentService studentService, TestService testService) {
        this.courseRepository = courseRepository;
        this.resultTestService = resultTestService;
        this.testService = testService;
        this.studentService = studentService;
    }

    public List<Course> showAll(){
        return courseRepository.findAll();
    }

    public  Course findOne(int id){
        return courseRepository.findById(id).orElse(null);
    }

    public  Course customFindById(int id){
        return courseRepository.getCourseById(id);
    }

    @Transactional
    public void save(Course course){
        courseRepository.save(course);
    }

    @Transactional
    public void update(int id, Course updatedCourse){
        updatedCourse.setId(id);
        courseRepository.save(updatedCourse);
    }

    @Transactional
    public List<Course> selectCourse(int id){
        return courseRepository.getCourse(id);
    }
    @Transactional
    public List<Course> selectCourseForStudentGroup(int id){
        return courseRepository.getCourseForStudent(id);
    }

    @Transactional
    public void delete(int id){
        courseRepository.deleteById(id);
    }


}

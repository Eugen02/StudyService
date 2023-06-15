package com.zhoholiev.studyservice.StudyService.services;

import com.zhoholiev.studyservice.StudyService.models.Course;
import com.zhoholiev.studyservice.StudyService.models.CourseBlock;
import com.zhoholiev.studyservice.StudyService.repositories.CourseBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CourseBlockService {

    CourseBlockRepository courseBlockRepository;

    @Autowired
    public CourseBlockService(CourseBlockRepository courseBlockRepository) {
        this.courseBlockRepository = courseBlockRepository;
    }

    public List<CourseBlock> showAll(){
        return courseBlockRepository.findAll();
    }

    public  CourseBlock findOne(int id){
        return courseBlockRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(CourseBlock course){
        courseBlockRepository.save(course);
    }

    @Transactional
    public void update(int id, CourseBlock updatedCourse){
        updatedCourse.setId(id);
        courseBlockRepository.save(updatedCourse);
    }

    @Transactional
    public void delete(int id){
        courseBlockRepository.deleteById(id);
    }
}

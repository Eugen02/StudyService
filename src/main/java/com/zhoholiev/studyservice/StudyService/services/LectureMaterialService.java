package com.zhoholiev.studyservice.StudyService.services;

import com.zhoholiev.studyservice.StudyService.models.LectureMaterial;
import com.zhoholiev.studyservice.StudyService.models.Role;
import com.zhoholiev.studyservice.StudyService.repositories.LectureMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LectureMaterialService {

    LectureMaterialRepository lectureMaterialRepository;

    @Autowired
    public LectureMaterialService(LectureMaterialRepository lectureMaterialRepository) {
        this.lectureMaterialRepository = lectureMaterialRepository;
    }

    public List<LectureMaterial> showAll(){
        return lectureMaterialRepository.findAll();
    }

    public  LectureMaterial findOne(int id){
        return lectureMaterialRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(LectureMaterial role){
        lectureMaterialRepository.save(role);
    }

    @Transactional
    public void update(int id, LectureMaterial updatedLecture){
        updatedLecture.setId(id);
        lectureMaterialRepository.save(updatedLecture);
    }

    @Transactional
    public void delete(int id){
        lectureMaterialRepository.deleteById(id);
    }

@Transactional
    public void updateLecture(LectureMaterial lectureMaterial){
        lectureMaterialRepository.updateLecture(lectureMaterial.getName(),
                lectureMaterial.getDescription(),
                lectureMaterial.getLink(),
                lectureMaterial.getCourse().getId(),
                lectureMaterial.getLectureMaterialToCourseBlock().getId(),
                lectureMaterial.getNameVideo(),
                lectureMaterial.getId());
}


}

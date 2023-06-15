package com.zhoholiev.studyservice.StudyService.services;

import com.zhoholiev.studyservice.StudyService.models.Role;
import com.zhoholiev.studyservice.StudyService.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> showAll(){
        return roleRepository.findAll();
    }

    public  Role findOne(int id){
        return roleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Role role){
        roleRepository.save(role);
    }

    @Transactional
    public void update(int id, Role updatedRole){
        updatedRole.setId(id);
        roleRepository.save(updatedRole);
    }

    @Transactional
    public void delete(int id){
        roleRepository.deleteById(id);
    }

}

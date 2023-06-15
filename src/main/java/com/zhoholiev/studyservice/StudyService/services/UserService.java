package com.zhoholiev.studyservice.StudyService.services;

import com.zhoholiev.studyservice.StudyService.models.Student;
import com.zhoholiev.studyservice.StudyService.models.StudentGroup;
import com.zhoholiev.studyservice.StudyService.models.Teacher;
import com.zhoholiev.studyservice.StudyService.models.User;
import com.zhoholiev.studyservice.StudyService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {


    private final UserRepository userRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       StudentService studentService, TeacherService teacherService) {

        this.studentService = studentService;
        this.teacherService = teacherService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> showAll() {
        return userRepository.findAll();
    }

    public User findOne(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPassword);
        userRepository.save(user);
    }

    @Transactional
    public void update(int id, User updatedUser) {
        if (updatedUser.getRole().getId() == 4 && null != userRepository.findById(id).get().getTeacher()) {
            teacherService.delete(userRepository.findById(id).get().getTeacher().getId());
        }

        if (updatedUser.getRole().getId() == 4) {
            Student student = new Student();
            student.setUserStudent(updatedUser);
            updatedUser.setStudent(student);
            studentService.save(student);
        }

        Optional<User> user = userRepository.findById(id);
        updatedUser.setId(id);
        if (!Objects.equals(updatedUser.getUserPassword().substring(0, 4), "$2a$")) {
            String encodedPassword = passwordEncoder.encode(updatedUser.getUserPassword());
            updatedUser.setUserPassword(encodedPassword);
        } else updatedUser.setUserPassword(user.get().getUserPassword());
        if (updatedUser.getRole() == null) {
            updatedUser.setRole(user.get().getRole());
        }
        userRepository.save(updatedUser);
    }

    @Transactional
    public void updateSpecial(int id, User updatedUser) {
        if (updatedUser.getRole().getId() == 4 && null != userRepository.findById(id).get().getTeacher()) {
            teacherService.delete(userRepository.findById(id).get().getTeacher().getId());
        }

        Optional<User> user = userRepository.findById(id);
        updatedUser.setId(id);
        if (!Objects.equals(updatedUser.getUserPassword().substring(0, 4), "$2a$")) {
            String encodedPassword = passwordEncoder.encode(updatedUser.getUserPassword());
            updatedUser.setUserPassword(encodedPassword);
        } else updatedUser.setUserPassword(user.get().getUserPassword());
        if (updatedUser.getRole() == null) {
            updatedUser.setRole(user.get().getRole());
        }
        userRepository.save(updatedUser);
    }
    @Transactional
    public void updateUserGroup(int id, User updatedUser) {

        Optional<User> user = userRepository.findById(id);
        updatedUser.setId(id);
        if (!Objects.equals(updatedUser.getUserPassword().substring(0, 4), "$2a$")) {
            String encodedPassword = passwordEncoder.encode(updatedUser.getUserPassword());
            updatedUser.setUserPassword(encodedPassword);
        }
        userRepository.save(updatedUser);
    }


    @Transactional
    public void update(int id, User updatedUser, Teacher updatedTeacher) {
        if (null != userRepository.findById(id).get().getStudent()) {
            studentService.delete(userRepository.findById(id).get().getStudent().getId());
        }
        Teacher teacher = new Teacher();
        teacher.setPosition(updatedTeacher.getPosition());
        teacher.setUser(updatedUser);
        updatedUser.setTeacher(teacher);
        if (userRepository.findById(id).get().getTeacher() == null) {
            teacherService.save(teacher);
        } else teacherService.update(userRepository.findById(id).get().getTeacher().getId(), teacher);

        Optional<User> user = userRepository.findById(id);
        updatedUser.setId(id);

        if (!Objects.equals(updatedUser.getUserPassword().substring(0, 4), "$2a$")) {
            String encodedPassword = passwordEncoder.encode(updatedUser.getUserPassword());
            updatedUser.setUserPassword(encodedPassword);
        } else updatedUser.setUserPassword(user.get().getUserPassword());
        if (updatedUser.getRole() == null) {
            updatedUser.setRole(user.get().getRole());
        }
        userRepository.save(updatedUser);
    }


    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

}

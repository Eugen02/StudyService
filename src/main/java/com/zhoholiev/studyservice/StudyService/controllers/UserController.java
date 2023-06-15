package com.zhoholiev.studyservice.StudyService.controllers;

import com.zhoholiev.studyservice.StudyService.models.*;
import com.zhoholiev.studyservice.StudyService.security.PersonDetails;
import com.zhoholiev.studyservice.StudyService.services.*;
import com.zhoholiev.studyservice.StudyService.util.DashboardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    UserService userService;
    RoleService roleService;

    TeacherService teacherService;

    StudentService studentService;
    StudentGroupService studentGroupService;

    CourseService courseService;

    @Autowired
    public UserController(UserService userService, RoleService roleService,
                          TeacherService teacherService, StudentService studentService,
                          StudentGroupService studentGroupService, CourseService courseService) {
        this.userService = userService;
        this.roleService = roleService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.studentGroupService = studentGroupService;
        this.courseService = courseService;
    }

    @GetMapping("/welcome")
    public String userPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("person", personDetails.getUser());
        if (personDetails.getUser().getStudent() != null) {
            model.addAttribute("courses", courseService.selectCourseForStudentGroup(personDetails.getUser().getStudent().getStudentGroup().getId()));
        }
        if (personDetails.getUser().getTeacher() != null) {
            model.addAttribute("coursesTeacher", courseService.selectCourse(personDetails.getUser().getTeacher().getId()));
        }
        return "pages/user/welcome_page";
    }

    @GetMapping("/user_edit/{id}")
    public String showInfo(@PathVariable int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("updatedUser", userService.findOne(id));
        model.addAttribute("teacher", ((userService.findOne(id).getTeacher() == null) ? new Teacher() : userService.findOne(id).getTeacher()));
        model.addAttribute("student", ((userService.findOne(id).getStudent() == null) ? new Student() : userService.findOne(id).getStudent()));
        model.addAttribute("group", studentGroupService.showAll());
        model.addAttribute("allRoles", roleService.showAll());
        model.addAttribute("user", personDetails.getUser());
        return "pages/user/edit_user";
    }

    @PatchMapping("/edit_user/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("updatedUser") @Validated User user,
                             @ModelAttribute("teacher") Teacher teacherPos, @ModelAttribute("student") Student studentPos,
                             @ModelAttribute("group") StudentGroup studentGroup,
                             BindingResult bindingResult, Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.showAll());
            return "pages/user/edit_user";
        }
        if (user.getRole().getId() == 5) {
            userService.update(id, user, teacherPos);
        } else {
            userService.update(id, user);
        }
        return "redirect:/showInfo";
    }


    @GetMapping("/showInfo")
    public String showUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user_c", personDetails.getUser());
        model.addAttribute("user", personDetails.getUser());
        return "pages/user/show_info";
    }


    @GetMapping("/showInfo/{id}")
    public String showUserInfoById(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (userService.findOne(id).getRole().getName().equals("ROLE_STUDENT")){
        List<Course> course = studentService.findOne(userService.findOne(id).getStudent().getId()).getStudentGroup().getCourses();
        List<DashboardUtil> dashboardUtils = new ArrayList<>(course.size());
        for (int i = 0; i < course.size(); i++) {
            dashboardUtils.add(new DashboardUtil(course.get(i),  course.get(i).getTests(), userService.findOne(id).getStudent()));
        }
        model.addAttribute("dashboardUtil", dashboardUtils);
        }
        model.addAttribute("user_c", userService.findOne(id));
        model.addAttribute("user", personDetails.getUser());
        return "pages/user/show_info";
    }

}

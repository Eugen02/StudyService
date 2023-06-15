package com.zhoholiev.studyservice.StudyService.controllers;

import com.zhoholiev.studyservice.StudyService.security.PersonDetails;
import com.zhoholiev.studyservice.StudyService.services.CourseService;
import com.zhoholiev.studyservice.StudyService.services.TestService;
import com.zhoholiev.studyservice.StudyService.util.analise.AnaliseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TeacherAnaliseController {

    CourseService courseService;

    TestService testService;

    @Autowired
    public TeacherAnaliseController(CourseService courseService, TestService testService) {
        this.courseService = courseService;
        this.testService = testService;
    }

    @GetMapping("/analise")
    public String getCourseMain(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("courses", courseService.selectCourse(personDetails.getUser().getTeacher().getId()));
        model.addAttribute("teacher", personDetails.getUser().getTeacher());
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/analise/course_main";
    }

    @GetMapping("/analise_course/{id}")
    public String getCourseAnalise(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/analise/course_analise";
    }

    @GetMapping("/check_test_main_analise/{id}")
    public String getTestMain(@PathVariable("id") int id,  Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("tests", courseService.findOne(id).getTests());
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/analise/test_main";
    }

    @GetMapping("/analise_test/{id}")
    public String getTestAnalise(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        AnaliseTest analiseTest = new AnaliseTest(testService.findOne(id));
        model.addAttribute("user", personDetails.getUser());
        model.addAttribute("analiseTest", analiseTest);
        return "pages/course/analise/test_analise";
    }
}

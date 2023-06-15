package com.zhoholiev.studyservice.StudyService.controllers;

import com.zhoholiev.studyservice.StudyService.models.Course;
import com.zhoholiev.studyservice.StudyService.models.CourseBlock;
import com.zhoholiev.studyservice.StudyService.models.LectureMaterial;
import com.zhoholiev.studyservice.StudyService.models.ResultLectureMaterial;
import com.zhoholiev.studyservice.StudyService.security.PersonDetails;
import com.zhoholiev.studyservice.StudyService.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CourseController {

    CourseService courseService;
    TeacherService teacherService;
    StudentService studentService;
    CourseBlockService courseBlockService;
    TestService testService;
    LectureMaterialService lectureMaterialService;

    ResultLectureMaterialService resultLectureMaterialService;

    @Autowired
    public CourseController(CourseService courseService, TeacherService teacherService,
                            StudentService studentService, CourseBlockService courseBlockService,
                            TestService testService, LectureMaterialService lectureMaterialService,
                            ResultLectureMaterialService resultLectureMaterialService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.courseBlockService = courseBlockService;
        this.testService = testService;
        this.lectureMaterialService = lectureMaterialService;
        this.resultLectureMaterialService = resultLectureMaterialService;
    }

    @GetMapping("/lecture_material/{id}")
    public String showLectureMaterial(@PathVariable("id") int id,
                                      Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("lectureMaterial", lectureMaterialService.findOne(id));
        if (personDetails.getUser().getStudent() != null) {
            model.addAttribute("resultLecture", resultLectureMaterialService.findByStudentAndLecture(studentService.findOne(personDetails.getUser().getStudent().getId()), lectureMaterialService.findOne(id)));
        }
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/show_lecture_material";
    }


    @GetMapping("/lecture_material_edit/{id}")
    public String editLectureMaterial(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("lecture_material", lectureMaterialService.findOne(id));
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/edit_lecture_material";
    }


    @DeleteMapping("/lectureMaterial_remove/{id}")
    public String deleteGroup(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        CourseBlock courseBlock = lectureMaterialService.findOne(id).getLectureMaterialToCourseBlock();
        courseBlock.getLectureMaterials().remove(lectureMaterialService.findOne(id));
        lectureMaterialService.delete(id);
        courseBlockService.update(courseBlock.getId(), courseBlock);
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/course_edit/" + courseBlock.getCourseCourseBlock().getId();
    }

    @DeleteMapping("/course_block_remove/{id}")
    public String deleteCourseBlock(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Course course = courseBlockService.findOne(id).getCourseCourseBlock();
        CourseBlock courseBlock = courseBlockService.findOne(id);
        course.getCourseBlocks().remove(courseBlock);
        courseBlockService.delete(id);
        courseService.update(course.getId(), course);
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/course_edit/" + course.getId();
    }

    @DeleteMapping("/test_remove/{id}")
    public String deleteTest(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        CourseBlock courseBlock = testService.findOne(id).getTestToCourseBlock();
        courseBlock.getTests().remove(testService.findOne(id));
        testService.delete(id);
        courseBlockService.update(courseBlock.getId(), courseBlock);
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/course_edit/" + courseBlock.getCourseCourseBlock().getId();
    }

    @GetMapping("/course_stat/{id}")
    public String courseData(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("courses", teacherService.findOne(id).getCourses());
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/course_statistics";
    }

    @GetMapping("/course_test_view/{id}")
    public String courseTestView(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("tests", courseService.findOne(id).getTests());
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/course_test_view";
    }


    @GetMapping("/course_test_results/{id}")
    public String courseTestViewResult(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("resultTests", testService.findOne(id).getResultTest());
        model.addAttribute("user", personDetails.getUser());
        model.addAttribute("course", testService.findOne(id).getCourseTest());
        return "pages/course/student_result_test_for_teacher";
    }

}

package com.zhoholiev.studyservice.StudyService.controllers;

import com.zhoholiev.studyservice.StudyService.models.*;
import com.zhoholiev.studyservice.StudyService.security.PersonDetails;
import com.zhoholiev.studyservice.StudyService.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TeacherController {

    CourseService courseService;
    TeacherService teacherService;
    UserService userService;
    StudentGroupService studentGroupService;

    CourseBlockService courseBlockService;

    LectureMaterialService lectureMaterialService;

    private static final String UPLOADED_FOLDER_VIDEO = "F://диплом//StudyService//src//main//resources//static//video//";
    private static final String UPLOADED_FOLDER_IMAGE = "F://диплом//StudyService//src//main//resources//static//images//";


    @Autowired
    public TeacherController(CourseService courseService,
                             TeacherService teacherService,
                             UserService userService,
                             StudentGroupService studentGroupService,
                             CourseBlockService courseBlockService,
                             LectureMaterialService lectureMaterialService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.userService = userService;
        this.studentGroupService = studentGroupService;
        this.courseBlockService = courseBlockService;
        this.lectureMaterialService = lectureMaterialService;
    }

    @GetMapping("/show_info_courses")
    public String mainPageCoursesInfo(@ModelAttribute("new_course") Course course, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("courses", courseService.selectCourse(personDetails.getUser().getTeacher().getId()));
        model.addAttribute("teacher", personDetails.getUser().getTeacher());
        model.addAttribute("user", personDetails.getUser());
        return "/pages/teacher/check_course";
    }

    @GetMapping("/statics")
    public String mainPageCoursesStatics(@ModelAttribute("courses") Course course, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("courses", courseService.selectCourse(personDetails.getUser().getTeacher().getId()));
        model.addAttribute("teacher", personDetails.getUser().getTeacher());
        model.addAttribute("user", personDetails.getUser());
        return "/pages/teacher/statics_teacher";
    }

    @GetMapping("/course_create")
    public String courseCreateGet(@ModelAttribute("new_course") Course course) {
        return "/pages/teacher/course_create";
    }

    @PostMapping("/course_create")
    public String courseCreatePost(@ModelAttribute("new_course") Course course,
                                   Model model,
                                   @RequestParam("file") MultipartFile file
    ) {

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER_IMAGE + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Teacher teacher = teacherService.findOne(personDetails.getUser().getTeacher().getId());
        if (teacher.getCourses() == null) {
            teacher.setCourses(Collections.singletonList(course));
        } else teacher.getCourses().add(course);
        course.setTeacher(teacher);
        if (file.getOriginalFilename().equals("")) {
            course.setLinkPage(("/images/standart_course_image.png"));
        } else {
            course.setLinkPage("/images/" + file.getOriginalFilename());
        }
        teacherService.save(teacher);
        courseService.save(course);
        model.addAttribute("courses", courseService.selectCourse(personDetails.getUser().getTeacher().getId()));
        return "redirect:/show_info_courses";
    }

    @DeleteMapping("/remove_course/{id}")
    public String deleteGroup(@PathVariable("id") int id, Model model) {
        int teacherId = courseService.findOne(id).getTeacher().getId();
        courseService.delete(id);
        model.addAttribute("courses", courseService.selectCourse(teacherId));
        return "redirect:/show_info_courses";
    }

    @GetMapping("/course_group_edit/{id}")
    public String showGroupsToEdit(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<StudentGroup> studentGroups = courseService.findOne(id).getStudentGroups();
        List<StudentGroup> studentGroupsWhoIsnt = studentGroupService.showAll();
        ;
        studentGroupsWhoIsnt.removeAll(studentGroups);
        model.addAttribute("groupsWhoInCourse", courseService.findOne(id).getStudentGroups());
        model.addAttribute("groupWhoAdd", studentGroupsWhoIsnt);
        model.addAttribute("courseWhoEdit", courseService.findOne(id));
        model.addAttribute("user", personDetails.getUser());
        return "pages/teacher/add_remove_group_to_course";
    }


    @GetMapping("/course_edit_add/{id}")
    public String addNewGroupToCourse(@PathVariable("id") int id,
                                      @RequestParam(value = "idg", required = false) Integer idG,
                                      Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        StudentGroup studentGroup = studentGroupService.findOne(id);
        Course course = courseService.findOne(idG);
        if (studentGroup.getCourses() == null) {
            studentGroup.setCourses(Collections.singletonList(course));
        } else studentGroup.getCourses().add(course);
        if (course.getStudentGroups() == null) {
            course.setStudentGroups(Collections.singletonList(studentGroup));
        } else course.getStudentGroups().add(studentGroup);
        studentGroupService.save(studentGroup);
        courseService.save(course);


        List<StudentGroup> studentGroups = courseService.findOne(idG).getStudentGroups();
        List<StudentGroup> studentGroupsWhoIsnt = studentGroupService.showAll();
        ;
        studentGroupsWhoIsnt.removeAll(studentGroups);
        model.addAttribute("groupsWhoInCourse", courseService.findOne(idG).getStudentGroups());
        model.addAttribute("groupWhoAdd", studentGroupsWhoIsnt);
        model.addAttribute("courseWhoEdit", courseService.findOne(idG));
        model.addAttribute("user", personDetails.getUser());
        return "pages/teacher/add_remove_group_to_course";
    }

    @GetMapping("/course_edit_remove/{id}")
    public String removeGroupFromCourse(@PathVariable("id") int id,
                                        @RequestParam(value = "idg", required = false) Integer idG,
                                        Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        StudentGroup studentGroup = studentGroupService.findOne(id);
        Course course = courseService.findOne(idG);
        studentGroup.getCourses().remove(course);
        course.getStudentGroups().remove(studentGroup);
        studentGroupService.update(id, studentGroup);
        courseService.update(idG, course);


        List<StudentGroup> studentGroups = courseService.findOne(idG).getStudentGroups();
        List<StudentGroup> studentGroupsWhoIsnt = studentGroupService.showAll();
        studentGroupsWhoIsnt.removeAll(studentGroups);
        model.addAttribute("groupsWhoInCourse", courseService.findOne(idG).getStudentGroups());
        model.addAttribute("groupWhoAdd", studentGroupsWhoIsnt);
        model.addAttribute("courseWhoEdit", courseService.findOne(idG));
        model.addAttribute("user", personDetails.getUser());

        return "pages/teacher/add_remove_group_to_course";
    }

    @GetMapping("/course_edit/{id}")
    public String editCourse(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("courseBlocks", courseService.findOne(id).getCourseBlocks());
        model.addAttribute("course", courseService.customFindById(id));
        model.addAttribute("tests", courseService.findOne(id).getTests());
        model.addAttribute("lectureMaterial", courseService.findOne(id).getLectureMaterials());
        model.addAttribute("newCourseBlock", new CourseBlock());
        model.addAttribute("newCourseData", courseService.findOne(id));
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/edit_course_page";
    }

    @PostMapping("/create_new_course_block/{id}")
    public String createNewCourseBlock(@PathVariable("id") int id, @ModelAttribute("newCourseBlock") CourseBlock courseBlocks,
                                       RedirectAttributes redirectAttributest, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Course one = courseService.findOne(id);
        CourseBlock courseBlock = new CourseBlock();
        courseBlock.setDescription(courseBlocks.getDescription());
        courseBlock.setNameBlock(courseBlocks.getNameBlock());
        if (one.getCourseBlocks() == null) {
            one.setCourseBlocks(Collections.singletonList(courseBlock));
        } else one.getCourseBlocks().add(courseBlock);
        courseBlock.setCourseCourseBlock(one);
        courseBlockService.save(courseBlock);
        courseService.update(id, one);
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/course_edit/" + id;
    }

    @GetMapping("/create_lecture_material/{id}")
    public String createPageLectureMaterial(@PathVariable("id") int id,
                                            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("newLectureMaterial", new LectureMaterial());
        model.addAttribute("selectedCourseBlock", courseBlockService.findOne(id));
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/create_lecture_material";
    }

    @GetMapping("/saw_students/{id}")
    public String checkStudentGroupByTeacher(@PathVariable("id") int id,
                                             @RequestParam(value = "idg", required = false) Integer idG,
                                             Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        model.addAttribute("user", personDetails.getUser());
        model.addAttribute("group", studentGroupService.findOne(id));
        model.addAttribute("course", courseService.findOne(idG));
        return "pages/teacher/check_groups";
    }

    @PostMapping("/create_lecture_material/{id}")
    public String createLectureMaterial(@PathVariable("id") int id,
                                        @ModelAttribute("newLectureMaterial") LectureMaterial lecture,
                                        Model model) {
        LectureMaterial lectureMaterial = new LectureMaterial();
        lectureMaterial.setDescription(lecture.getDescription());
        lectureMaterial.setName(lecture.getName());
        lectureMaterial.setNameVideo(lecture.getNameVideo());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        CourseBlock courseBlock = courseBlockService.findOne(id);
        lectureMaterial.setLink(lectureMaterial.getLink());
        if (courseBlock.getLectureMaterials() == null) {
            courseBlock.setLectureMaterials(Collections.singletonList(lectureMaterial));
        } else courseBlock.getLectureMaterials().add(lectureMaterial);
        lectureMaterial.setLectureMaterialToCourseBlock(courseBlock);
        lectureMaterialService.save(lectureMaterial);
        courseBlockService.update(id, courseBlock);
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/course_edit/" + courseBlockService.findOne(id).getCourseCourseBlock().getId();
    }

    @PostMapping("/edit_course_data/{id}")
    public String editCourseData(@PathVariable("id") int id,
                                 @ModelAttribute("newCourseData") Course course,
                                 Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Course oldDataCourse = courseService.findOne(id);
        oldDataCourse.setName(course.getName());
        oldDataCourse.setDescription(course.getDescription());
        courseService.update(id, oldDataCourse);
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/course_edit/" + id;
    }

    @PostMapping("/change_photo/{id}")
    public String editCourseDataPhoto(@PathVariable("id") int id,
                                      @RequestParam("file") MultipartFile file,
                                      Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Course oldDataCourse = courseService.findOne(id);

        if (!oldDataCourse.getLinkPage().equals("/images/standart_course_image.png")) {
            String path = new File("").getAbsolutePath();
            Files.delete(Paths.get(path + "\\src\\main\\resources\\static" + oldDataCourse.getLinkPage().replace("/", "\\")));
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER_IMAGE + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        oldDataCourse.setLinkPage(("/images/" + file.getOriginalFilename()));
        courseService.update(id, oldDataCourse);
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/course_edit/" + id;
    }

    @PostMapping("/remove_photo/{id}")
    public String editCourseDataPhotoDelete(@PathVariable("id") int id,
                                            Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Course oldDataCourse = courseService.findOne(id);
        String path = new File("").getAbsolutePath();

        if (!oldDataCourse.getLinkPage().equals("/images/standart_course_image.png")) {
            Files.delete(Paths.get(path + "\\src\\main\\resources\\static" + oldDataCourse.getLinkPage().replace("/", "\\")));

        }
        oldDataCourse.setLinkPage(("/images/standart_course_image.png"));
        courseService.update(id, oldDataCourse);
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/course_edit/" + id;
    }

    @PostMapping("/change_lecture_material/{id}")
    public String editLectureMaterial(@PathVariable("id") int id,
                                      @RequestParam("file") MultipartFile file,
                                      Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Course oldDataCourse = courseService.findOne(id);

        if (!oldDataCourse.getLinkPage().equals("/images/standart_course_image.png")) {
            String path = new File("").getAbsolutePath();
            Files.delete(Paths.get(path + "\\src\\main\\resources\\static" + oldDataCourse.getLinkPage().replace("/", "\\")));
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER_IMAGE + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        oldDataCourse.setLinkPage(("/images/" + file.getOriginalFilename()));
        courseService.update(id, oldDataCourse);
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/course_edit/" + id;
    }

    @PostMapping("/remove_video/{id}")
    public String editLectureMaterialDelete(@PathVariable("id") int id,
                                            Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        LectureMaterial lectureMaterial = lectureMaterialService.findOne(id);
        lectureMaterial.setLink("#");
        lectureMaterial.setNameVideo("#");
        lectureMaterialService.update(id, lectureMaterial);
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/lecture_material_edit/" + id;
    }

    @PostMapping("/change_video/{id}")
    public String editLectureMaterialChange(@PathVariable("id") int id,
                                            @ModelAttribute("newLectureName") LectureMaterial material,
                                            Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        LectureMaterial lectureMaterial = lectureMaterialService.findOne(id);
        lectureMaterial.setLink(material.getLink());
        lectureMaterial.setNameVideo(material.getNameVideo());
        lectureMaterialService.update(id, lectureMaterial);
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/lecture_material_edit/" + id;
    }

    @PostMapping("/change_name/{id}")
    public String editLectureMaterialChangeName(@PathVariable("id") int id,
                                                @ModelAttribute("newLectureName") LectureMaterial material,
                                                Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        LectureMaterial lectureMaterial = lectureMaterialService.findOne(id);
        lectureMaterial.setName(material.getName());
        lectureMaterialService.update(id, lectureMaterial);
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/lecture_material_edit/" + id;
    }

    @PostMapping("/change_description/{id}")
    public String editLectureMaterialChangeDescription(@PathVariable("id") int id,
                                                       @ModelAttribute("newLectureDescription") LectureMaterial material, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        LectureMaterial lectureMaterial = lectureMaterialService.findOne(id);
        String replace = material.getDescription().replace("\n", " ");
        lectureMaterial.setDescription(replace);
        lectureMaterialService.update(id, lectureMaterial);
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/lecture_material_edit/" + id;
    }

}

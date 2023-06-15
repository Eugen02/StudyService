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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Controller
public class AdminController {

    UserService userService;
    CourseService courseService;
    RoleService roleService;
    StudentGroupService studentGroupService;
    StudentService studentService;

    TestService testService;

    @Autowired
    public AdminController(UserService userService,
                           CourseService courseService,
                           RoleService roleService,
                           StudentGroupService studentGroupService,
                           StudentService studentService,
                           TestService testService) {
        this.userService = userService;
        this.courseService = courseService;
        this.roleService = roleService;
        this.studentGroupService = studentGroupService;
        this.studentService = studentService;
        this.testService = testService;
    }

    @GetMapping("/redact_users")
    public String redactUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<User> users = userService.showAll();
        List<User> usersToTeacher = new ArrayList<>();
        Iterator<User> catIterator = users.iterator();
        while (catIterator.hasNext()) {
            User nextCat = catIterator.next();
            if (nextCat.getId() == personDetails.getUser().getId()) {
                catIterator.remove();
            }
            if (nextCat.getRole().getId() == 3) {
                catIterator.remove();
                usersToTeacher.add(nextCat);
            }
        }
        model.addAttribute("users", users);
        model.addAttribute("usersToTeacher", usersToTeacher);
        model.addAttribute("user", personDetails.getUser());
        return "pages/admin/redact_users";
    }

    @GetMapping("/redact_group")
    public String redactGroup(@ModelAttribute("group") StudentGroup studentGroup, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<StudentGroup> studentGroups = studentGroupService.showAll();
        for (int i = 0; i < studentGroups.size(); i++) {
            studentGroups.get(i).setStudentNumber(studentGroups.get(i).getStudents().size());
        }
        model.addAttribute("groups", studentGroups);
        model.addAttribute("user", personDetails.getUser());
        return "/pages/admin/redact_group";
    }

    @GetMapping("/redact_coursers")
    public String redactCourses(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("courses", userService.showAll());
        model.addAttribute("user", personDetails.getUser());
        return "pages/admin/redact_course";
    }


    @DeleteMapping("/remove/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getUser());
        userService.delete(id);
        return "redirect:/redact_users";
    }

    @DeleteMapping("/remove_group/{id}")
    public String deleteGroup(@PathVariable("id") int id, Model model) {
        studentGroupService.delete(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/redact_group";
    }

    @GetMapping("/newTeacher/{id}")
    public String addNewTeacher(@PathVariable int id, Model model) {
        User user = userService.findOne(id);
        user.setRole(roleService.findOne(5));
        Teacher teacher = (userService.findOne(id).getTeacher() == null) ? new Teacher("Преподаватель") : userService.findOne(id).getTeacher();
        System.out.println(
                teacher.toString()
        );
        userService.update(id, user, teacher);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<User> users = userService.showAll();
        List<User> usersToTeacher = new ArrayList<>();
        Iterator<User> catIterator = users.iterator();
        while (catIterator.hasNext()) {
            User nextCat = catIterator.next();
            if (nextCat.getId() == personDetails.getUser().getId()) {
                catIterator.remove();
            }
            if (nextCat.getRole().getId() == 3) {
                catIterator.remove();
                usersToTeacher.add(nextCat);
            }
        }
        model.addAttribute("users", users);
        model.addAttribute("usersToTeacher", usersToTeacher);
        model.addAttribute("user", personDetails.getUser());
        return "pages/admin/redact_users";
    }

    @GetMapping("/newGroup")
    public String insertDataGroup(@ModelAttribute("group") StudentGroup studentGroup, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getUser());
        return "pages/admin/create_group";
    }

    @PostMapping("/newGroup")
    public String createNewGroup(@ModelAttribute("group") StudentGroup studentGroup, Model model) {
        studentGroupService.save(studentGroup);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/redact_group";
    }

    @GetMapping("/group_edit/{id}")
    public String personalGroupView(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (studentGroupService.findOne(id).getStudents() == null) {
            studentGroupService.findOne(id).setStudents(new ArrayList<Student>());
        }
        List<Student> students = studentGroupService.findOne(id).getStudents();
        List<User> usersInGroup = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            usersInGroup.add(students.get(i).getUserStudent());
        }
        model.addAttribute("usersInGroup", usersInGroup);
        List<User> users = userService.showAll();
        Iterator<User> userIterator = users.iterator();
        while (userIterator.hasNext()) {
            User user = userIterator.next();
            if (Objects.equals(user.getRole().getName(), "ROLE_TEACHER") ||
                    Objects.equals(user.getRole().getName(), "ROLE_ADMIN") ||
                    Objects.equals(user.getRole().getName(), "ROLE_USER")) {
                userIterator.remove();
            } else if (user.getStudent() != null) {
                userIterator.remove();
            }
        }
        model.addAttribute("usersWhoAdd", users);
        model.addAttribute("groupWhoEdit", studentGroupService.findOne(id));
        model.addAttribute("user", personDetails.getUser());
        return "pages/admin/add_remove_student_to_group";
    }


    @GetMapping("/group_edit_add/{id}")
    public String addNewStudentToGroup(@PathVariable("id") int id,
                                       @RequestParam(value = "idg", required = false) Integer idG,
                                       Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Student student = new Student();
        User user = userService.findOne(id);
        StudentGroup studentGroup1 = studentGroupService.findOne(idG);
        student.setStudentGroup(studentGroup1);
        student.setUserStudent(user);
        studentGroup1.getStudents().add(student);
        studentService.save(student);
        studentGroupService.save(studentGroup1);
        user.setStudent(student);
        userService.updateUserGroup(user.getId(), user);
        if (studentGroupService.findOne(idG).getStudents() == null) {
            studentGroupService.findOne(idG).setStudents(new ArrayList<Student>());
        }
        List<Student> students = studentGroupService.findOne(idG).getStudents();
        List<User> usersInGroup = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            usersInGroup.add(students.get(i).getUserStudent());
        }
        model.addAttribute("usersInGroup", usersInGroup);
        List<User> users = userService.showAll();
        Iterator<User> userIterator = users.iterator();
        while (userIterator.hasNext()) {
            User user1 = userIterator.next();
            if (Objects.equals(user1.getRole().getName(), "ROLE_TEACHER") ||
                    Objects.equals(user1.getRole().getName(), "ROLE_ADMIN") ||
                    Objects.equals(user1.getRole().getName(), "ROLE_USER")) {
                userIterator.remove();
            } else if (user1.getStudent() != null) {
                userIterator.remove();
            }
        }
        model.addAttribute("usersWhoAdd", users);
        model.addAttribute("groupWhoEdit", studentGroupService.findOne(idG));
        model.addAttribute("user", personDetails.getUser());
        return "pages/admin/add_remove_student_to_group";
    }

    @GetMapping("/group_edit_remove/{id}")
    public String removeStudentFromGroup(@PathVariable("id") int id,
                                         @RequestParam(value = "idg", required = false) Integer idG,
                                         Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Student student = studentService.findOne(userService.findOne(id).getStudent().getId());
        User user = userService.findOne(id);
        user.setStudent(null);
        student.setUserStudent(user);
        studentService.delete(student.getId());
        userService.updateSpecial(id, user);
        StudentGroup studentGroup = studentGroupService.findOne(idG);
        studentGroup.getStudents().remove(student);
        studentGroupService.update(idG, studentGroup);
        if (studentGroupService.findOne(idG).getStudents() == null) {
            studentGroupService.findOne(idG).setStudents(new ArrayList<Student>());
        }
        List<Student> students = studentGroupService.findOne(idG).getStudents();
        List<User> usersInGroup = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            usersInGroup.add(students.get(i).getUserStudent());
        }
        List<User> users = userService.showAll();
        Iterator<User> userIterator = users.iterator();
        while (userIterator.hasNext()) {
            User user1 = userIterator.next();
            if (Objects.equals(user1.getRole().getName(), "ROLE_TEACHER") ||
                    Objects.equals(user1.getRole().getName(), "ROLE_ADMIN") ||
                    Objects.equals(user1.getRole().getName(), "ROLE_USER")) {
                userIterator.remove();
            } else if (user1.getStudent() != null) {
                userIterator.remove();
            }
        }
        model.addAttribute("usersInGroup", usersInGroup);
        model.addAttribute("usersWhoAdd", users);
        model.addAttribute("groupWhoEdit", studentGroupService.findOne(idG));
        model.addAttribute("user", personDetails.getUser());
        return "pages/admin/add_remove_student_to_group";
    }


    @GetMapping("/statics_admin")
    public String staticsAdminShowCourses(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<Course> courses = courseService.showAll();
        model.addAttribute("all_courses", courses);
        model.addAttribute("user", personDetails.getUser());
        return "pages/admin/statics_admin_show";
    }

    @GetMapping("/course_info_admin/{id}")
    public String staticsAdminShowTests(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getUser());
        model.addAttribute("tests", testService.findByCourse(courseService.findOne(id)));
        return "pages/admin/statics_admin_show_course_tests";
    }

    @GetMapping("/test_info_admin/{id}")
    public String staticsAdminShowTestResult(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getUser());
        List<ResultTest> resultTest = testService.findOne(id).getResultTest();
        model.addAttribute("resultTests", testService.findOne(id).getResultTest());
        model.addAttribute("test", testService.findOne(id));
        return "pages/admin/statics_admin_show_result_tests";
    }


}

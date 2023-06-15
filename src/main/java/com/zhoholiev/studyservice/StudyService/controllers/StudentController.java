package com.zhoholiev.studyservice.StudyService.controllers;

import com.zhoholiev.studyservice.StudyService.models.*;
import com.zhoholiev.studyservice.StudyService.security.PersonDetails;
import com.zhoholiev.studyservice.StudyService.services.*;
import com.zhoholiev.studyservice.StudyService.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class StudentController {

    CourseService courseService;
    CourseBlockService courseBlockService;
    LectureMaterialService lectureMaterialService;
    TestService testService;
    AnswerService answerService;
    StudentService studentService;
    StudentGroupService studentGroupService;

    ResultTestService resultTestService;

    ResultAnswerService resultAnswerService;

    QuestService questService;

    HttpSessionBean httpSessionBean;

    ResultLectureMaterialService resultLectureMaterialService;

    @Autowired
    public StudentController(HttpSessionBean httpSessionBean,
                             QuestService questService,
                             ResultTestService resultTestService,
                             ResultAnswerService resultAnswerService,
                             CourseService courseService,
                             StudentGroupService studentGroupService,
                             CourseBlockService courseBlockService,
                             LectureMaterialService lectureMaterialService,
                             TestService testService,
                             AnswerService answerService,
                             StudentService studentService,
                             ResultLectureMaterialService resultLectureMaterialService) {
        this.courseService = courseService;
        this.courseBlockService = courseBlockService;
        this.lectureMaterialService = lectureMaterialService;
        this.testService = testService;
        this.answerService = answerService;
        this.studentService = studentService;
        this.studentGroupService = studentGroupService;
        this.resultTestService = resultTestService;
        this.resultAnswerService = resultAnswerService;
        this.questService = questService;
        this.httpSessionBean = httpSessionBean;
        this.resultLectureMaterialService = resultLectureMaterialService;
    }


    @GetMapping("/get_course_view_student/{id}")
    public String viewCourseStudent(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        model.addAttribute("course", courseService.findOne(id));
        if (personDetails.getUser().getStudent() != null) {
            model.addAttribute("student", studentService.findOne(personDetails.getUser().getStudent().getId()));
        }
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/show_course_for_student";
    }

    @GetMapping("/test_show_info/{id}")
    public String showTestInfo(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("test", testService.findOne(id));
        List<ResultTest> resultTest = testService.findOne(id).getResultTest();
        if (resultTestService.findByStudentAndTest(personDetails.getUser().getStudent(), testService.findOne(id)) == null ||
                testService.findOne(id).getResultTest().size() == 0) {
            ResultTestNotExist resultTestNotExist = new ResultTestNotExist(testService.findOne(id).getAttempt(), "The test has not started");
            model.addAttribute("result_test", resultTestNotExist);
        } else {
            model.addAttribute("result_test", resultTestService.findByStudentAndTest(
                    personDetails.getUser().getStudent(), testService.findOne(id)
            ));
        }
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/test/test_info_page";
    }

    @GetMapping("/test_start/{id}")
    public String testBegin(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        TestTake testTake = new TestTake();
        testTake.setCompleteTest(testTake.insertQuests(testService.findOne(id).getQuests()));
        httpSessionBean.setTestTake(new TestTake());
        httpSessionBean.setStartData(new Date());
        httpSessionBean.getTestTake().setCompleteTest(testTake.getCompleteTest());
        model.addAttribute("testTake", testTake);
        model.addAttribute("testID", testService.findOne(id));
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/test/test_page";
    }

    @PostMapping("/test_start/{id}")
    public String testEnd(@PathVariable("id") int id, @ModelAttribute("testTake") TestTake testTake, Model model) {
        model.addAttribute("testID", testService.findOne(id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getUser());
        ResultTest resultTest;
        Test test = testService.findOne(id);
        ResultAnswer resultAnswer;
        httpSessionBean.getTestTake().setTest(test);
        if (resultTestService.findByStudentAndTest(personDetails.getUser().getStudent(), test) == null) {
            resultTest = new ResultTest();
            resultTest.setStartDate(httpSessionBean.getStartData());
            List<CompleteTest> completeTest = testTake.getCompleteTest();
            int size = completeTest.size();
            if (completeTest.size() < testService.findOne(id).getQuests().size()) {
                for (int i = 0; i < (testService.findOne(id).getQuests().size() - size); i++) {
                    CompleteTest completeTest1 = new CompleteTest();
                    completeTest.add(completeTest1);
                }
            }
            List<ResultAnswer> resultAnswerList = new ArrayList<>();
            for (int i = 0; i < testService.findOne(id).getQuests().size(); i++) {
                resultAnswer = new ResultAnswer();
                resultAnswer.setAnswerValueId(completeTest.get(i).getResultAnswerId());
                resultAnswer.setQuest_id(testService.findOne(id).getQuests().get(i).getId());
                if (completeTest.get(i).getResultAnswerId() == 0) {
                    resultAnswer.setAnswerIn(null);
                    resultAnswer.setCorectAnswer(0);
                } else {
                    resultAnswer.setAnswerIn(answerService.findOne(completeTest.get(i).getResultAnswerId()));
                    resultAnswer.setCorectAnswer(answerService.findOne(completeTest.get(i).getResultAnswerId()).getIsTrue());
                }
                resultAnswerList.add(resultAnswer);
            }
            for (int i = 0; i < resultAnswerList.size(); i++) {
                resultAnswerList.get(i).setResultTestInAnswer(resultTest);
            }
            double counter = 0;
            for (int i = 0; i < resultAnswerList.size(); i++) {
                if (resultAnswerList.get(i).getCorectAnswer() == 1) {
                    counter++;
                }
            }
            resultTest.setResultAnswers(resultAnswerList);
            resultTest.setTestToResult(test);
            resultTest.setStudent(personDetails.getUser().getStudent());
            double i1 = (int) ((counter / (double) resultAnswerList.size()) * 100);
            resultTest.setTestRating((int) i1);
            if (test.getResultTest() == null) {
                test.setResultTest(Collections.singletonList(resultTest));
            } else test.getResultTest().add(resultTest);
            resultTest.setCurrentAttempt(test.getAttempt() - 1);
            if (personDetails.getUser().getRole().getName().equals("ROLE_TEACHER")) {
                httpSessionBean.getTestTake().setResultTest(resultTest);
                httpSessionBean.getTestTake().setResultAnswerList(resultAnswerList);
                httpSessionBean.setEndData(new Date());
                long milliseconds = httpSessionBean.getEndData().getTime() - httpSessionBean.getStartData().getTime();
                httpSessionBean.setTransitTime((int) (milliseconds / (1000)));
                return "redirect:/result_test_page/" + resultTest.getId();
            }
            resultTest.setEndDate(new Date());
            long milliseconds = resultTest.getEndDate().getTime() - resultTest.getStartDate().getTime();
            resultTest.setTransitTime((int) (milliseconds / (1000)));
            httpSessionBean.setTransitTime((int) (milliseconds / (1000)));
            resultTestService.save(resultTest);
            testService.save(test);
            for (int i = 0; i < resultAnswerList.size(); i++) {
                resultAnswerService.save(resultAnswerList.get(i));
            }

            httpSessionBean.getTestTake().setResultTest(resultTest);
            httpSessionBean.getTestTake().setResultAnswerList(resultAnswerList);
        } else {
            ResultTest example = resultTestService.findByStudentAndTest(
                    personDetails.getUser().getStudent(), test
            );
            resultTest = new ResultTest();
            resultTest.setTestToResult(example.getTestToResult());
            resultTest.setTestRating(example.getTestRating());
            resultTest.setResultAnswers(example.getResultAnswers());
            resultTest.setStudent(example.getStudent());
            resultTest.setId(example.getId());
            resultTest.setStartDate(example.getStartDate());
            resultTest.setEndDate(example.getEndDate());
            resultTest.setTransitTime(example.getTransitTime());
            resultTest.setCurrentAttempt(example.getCurrentAttempt());
//            List<ResultAnswer> confirmedResultAnswer = resultTest.getResultAnswers();

            List<CompleteTest> completeTest = testTake.getCompleteTest();
            int size = completeTest.size();
            if (completeTest.size() < testService.findOne(id).getQuests().size()) {
                for (int i = 0; i < (testService.findOne(id).getQuests().size() - size); i++) {
                    CompleteTest completeTest1 = new CompleteTest();
                    completeTest.add(completeTest1);
                }
            }
            List<ResultAnswer> resultAnswerList = new ArrayList<>();
            List<ResultAnswer> resultAnswerListId = resultTest.getResultAnswers();
            for (int i = 0; i < testService.findOne(id).getQuests().size(); i++) {
                resultAnswer = new ResultAnswer();
                resultAnswer.setAnswerValueId(completeTest.get(i).getResultAnswerId());
                resultAnswer.setQuest_id(testService.findOne(id).getQuests().get(i).getId());
                if (completeTest.get(i).getResultAnswerId() == 0) {
                    resultAnswer.setId(resultAnswerListId.get(i).getId());
                    resultAnswer.setAnswerIn(null);
                    resultAnswer.setCorectAnswer(0);
                } else {
                    resultAnswer.setId(resultAnswerListId.get(i).getId());
                    resultAnswer.setAnswerIn(answerService.findOne(completeTest.get(i).getResultAnswerId()));
                    resultAnswer.setCorectAnswer(answerService.findOne(completeTest.get(i).getResultAnswerId()).getIsTrue());
                }
                resultAnswerList.add(resultAnswer);
            }
            for (int i = 0; i < resultAnswerList.size(); i++) {
                resultAnswerList.get(i).setResultTestInAnswer(resultTest);
            }
            double counter = 0;
            for (int i = 0; i < resultAnswerList.size(); i++) {
                if (resultAnswerList.get(i).getCorectAnswer() == 1) {
                    counter++;
                }
            }
            resultTest.setResultAnswers(resultAnswerList);
            double i1 = (int) ((counter / (double) resultAnswerList.size()) * 100);
            resultTest.setCurrentAttempt(resultTest.getCurrentAttempt() - 1);
            if (resultTest.getTestRating() < i1) {
                resultTest.setTestRating((int) i1);
                resultTest.setStartDate(httpSessionBean.getStartData());
                resultTest.setEndDate(new Date());
                long milliseconds = resultTest.getEndDate().getTime() - resultTest.getStartDate().getTime();
                resultTest.setTransitTime((int) (milliseconds / (1000)));

                for (int i = 0; i < resultAnswerList.size(); i++) {
                    resultAnswerService.update(resultAnswerList.get(i).getId(), resultAnswerList.get(i));
                }
                resultTestService.update(resultTest.getId(), resultTest);
                testService.update(id, test);

            } else {
                ResultTest oldResultTest = resultTestService.findByStudentAndTest(
                        personDetails.getUser().getStudent(), test
                );
                oldResultTest.setCurrentAttempt(oldResultTest.getCurrentAttempt() - 1);
                resultTestService.update(oldResultTest.getId(), resultTest);
            }
            httpSessionBean.getTestTake().setResultTest(resultTest);
            httpSessionBean.setEndData(new Date());
            long milliseconds = httpSessionBean.getEndData().getTime() - httpSessionBean.getStartData().getTime();
            httpSessionBean.setTransitTime((int) (milliseconds / (1000)));

            httpSessionBean.getTestTake().setResultAnswerList(resultAnswerList);
        }
        return "redirect:/result_test_page/" + resultTest.getId();
    }

    @GetMapping("/result_test_page/{id}")
    public String resultPageView(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        if (httpSessionBean.getTestTake() == null) {
            TestTake testTake = new TestTake();
            testTake.setCompleteTest(testTake.insertQuests(resultTestService.findOne(id).getTestToResult().getQuests()));
            testTake.setTest(resultTestService.findOne(id).getTestToResult());
            testTake.setResultTest(resultTestService.findOne(id));
            testTake.setResultAnswerList(resultTestService.findOne(id).getResultAnswers());
            int cor = 0;
            for (int i = 0; i < resultTestService.findOne(id).getResultAnswers().size(); i++) {
                if (resultTestService.findOne(id).getResultAnswers().get(i).getCorectAnswer() == 1) {
                    cor++;
                }
            }
            testTake.setCorrectAnswersAll(cor);
            int transitTime = resultTestService.findOne(id).getTransitTime();
            String s = (transitTime / 60) + ":" + (transitTime - (60 * (transitTime / 60)));
            testTake.setTime(s);
            model.addAttribute("testTake", testTake);
            model.addAttribute("user", personDetails.getUser());
            model.addAttribute("course", testTake.getTest().getCourseTest());
            return "pages/course/test/result_page";
        }

        TestTake testTake = httpSessionBean.getTestTake();
        int cor = 0;
        for (int i = 0; i < httpSessionBean.getTestTake().getResultTest().getResultAnswers().size(); i++) {
            if (httpSessionBean.getTestTake().getResultTest().getResultAnswers().get(i).getCorectAnswer() == 1) {
                cor++;
            }
        }
        testTake.setCorrectAnswersAll(cor);
        int transitTime = httpSessionBean.getTransitTime();
        String i = String.valueOf(transitTime / 60);
        String a = String.valueOf(transitTime - (60 * (transitTime / 60)));
        String s = ((i.length() < 2) ? ('0' + i) : i) +
                ":" +
                ((a.length() < 2) ? ('0' + a) : a);
        testTake.setTime(s);
        testTake.getResultTest().getTestToResult().setQuests(testTake.getTest().getQuests());
        if (personDetails.getUser().getRole().getName().equals("ROLE_TEACHER")) {
            model.addAttribute("testTake", testTake);
        } else {
            model.addAttribute("testTake", testTake);
        }
        httpSessionBean.setTestTake(null);
        model.addAttribute("user", personDetails.getUser());
        model.addAttribute("course", testTake.getTest().getCourseTest());
        return "pages/course/test/result_page";
    }

    @GetMapping("/dashboard_student")
    public String dashboardStudentShow(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<Course> course = studentService.findOne(personDetails.getUser().getStudent().getId()).getStudentGroup().getCourses();
        List<DashboardUtil> dashboardUtils = new ArrayList<>(course.size());
        for (int i = 0; i < course.size(); i++) {
            dashboardUtils.add(new DashboardUtil(course.get(i), course.get(i).getTests(), studentService.findOne(personDetails.getUser().getStudent().getId())));
        }
        model.addAttribute("dashboardUtil", dashboardUtils);
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/grade/dashboard";
    }

    @GetMapping("/personal_course_grade/{id}")
    public String personalCourseGradePage(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Course course = courseService.findOne(id);
        List<Test> tests = course.getTests();

        for (int i = 0; i < tests.size(); i++) {
            //int size = tests.get(i).getResultTest().size();
            for (int j = 0; j < tests.get(i).getResultTest().size(); j++) {
                if (tests.get(i).getResultTest().get(j).getStudent().getId()
                        != personDetails.getUser().getStudent().getId()) {
                    tests.get(i).getResultTest().remove(tests.get(i).getResultTest().get(j));
                    j--;
                }
            }
        }
        model.addAttribute("tests", tests);
        model.addAttribute("course", course);
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/grade/single_course_grade";
    }

    @GetMapping("/personal_course_grade_teacher/{id}")
    public String personalCourseGradePageTeacher(@PathVariable("id") int id,
                                                 @RequestParam(value = "idg", required = false) Integer idG,
                                                 Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        Course course = courseService.findOne(idG);
        List<Test> tests = course.getTests();

        for (int i = 0; i < tests.size(); i++) {
            for (int j = 0; j < tests.get(i).getResultTest().size(); j++) {
                if (tests.get(i).getResultTest().get(j).getStudent().getId() != studentService.findOne(id).getId()) {
                    tests.get(i).getResultTest().remove(tests.get(i).getResultTest().get(j));
                }
            }
        }
        model.addAttribute("tests", tests);
        model.addAttribute("course", course);
        model.addAttribute("student", studentService.findOne(id).getUserStudent());
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/grade/single_course_grade";
    }

    @GetMapping("/test_grade/{id}")
    public String testGrade(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        TestTake testTake = new TestTake();
        testTake.setCompleteTest(testTake.insertQuests(resultTestService.findOne(id).getTestToResult().getQuests()));
        testTake.setTest(resultTestService.findOne(id).getTestToResult());
        testTake.setResultTest(resultTestService.findOne(id));
        testTake.setResultAnswerList(resultTestService.findOne(id).getResultAnswers());

        model.addAttribute("resultTest", testTake);
        return "pages/course/grade/test_grade";
    }


    @GetMapping("/show_all_courses_for_student")
    public String showAllCoursesForStudent(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        model.addAttribute("allCourses", courseService.selectCourseForStudentGroup(
                studentService.findOne(personDetails.getUser().getStudent().getId()).getStudentGroup().getId()
        ));
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/test/show_all_courses_for_student";
    }


    @GetMapping("/show_my_group")
    public String showMyGroup(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getUser());
        model.addAttribute("student", studentService.findOne(personDetails.getUser().getStudent().getId()));
        StudentGroup studentGroup = studentService.findOne(personDetails.getUser().getStudent().getId()).getStudentGroup();
        studentGroup.setStudentNumber(studentGroup.getStudents().size());
        model.addAttribute("group", studentGroup);
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < studentGroup.getCourses().size(); i++) {
            teachers.add(studentGroup.getCourses().get(i).getTeacher());
        }
        model.addAttribute("teachers", teachers.stream().distinct().collect(Collectors.toList()));
        return "pages/user/MyGroup";
    }


    @PostMapping("/lecture_checkd_s/{id}")
    public String checkLecturePersonaly(@PathVariable("id") int id,
                                        Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        if (resultLectureMaterialService
                .findByStudentAndLecture(studentService.findOne(personDetails.getUser().getStudent().getId()), lectureMaterialService.findOne(id)) == null
        ) {
            ResultLectureMaterial resultLectureMaterial = new ResultLectureMaterial();
            Student student = studentService.findOne(personDetails.getUser().getStudent().getId());
            LectureMaterial lectureMaterial = lectureMaterialService.findOne(id);
            resultLectureMaterial.setStudentInResult(student);
            resultLectureMaterial.setLectureMaterialInResult(lectureMaterial);
            resultLectureMaterial.setCheckd(1);

            if (student.getResultLectureMaterials() == null) {
                student.setResultLectureMaterials(Collections.singletonList(resultLectureMaterial));
            } else student.getResultLectureMaterials().add(resultLectureMaterial);
            if (lectureMaterial.getResultLectureMaterials() == null) {
                lectureMaterial.setResultLectureMaterials(Collections.singletonList(resultLectureMaterial));
            } else lectureMaterial.getResultLectureMaterials().add(resultLectureMaterial);
            resultLectureMaterialService.save(resultLectureMaterial);
            studentService.update(student.getId(), student);
            lectureMaterialService.update(lectureMaterial.getId(), lectureMaterial);
        }

        model.addAttribute("user", personDetails.getUser());
        return "redirect:/lecture_material/" + id;
    }

}

package com.zhoholiev.studyservice.StudyService.controllers;

import com.zhoholiev.studyservice.StudyService.models.*;
import com.zhoholiev.studyservice.StudyService.security.PersonDetails;
import com.zhoholiev.studyservice.StudyService.services.*;
import com.zhoholiev.studyservice.StudyService.util.CompleteTest;
import com.zhoholiev.studyservice.StudyService.util.HttpSessionBean;
import com.zhoholiev.studyservice.StudyService.util.TestTake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class TestController {
    static int counterTest = 1;
    RoleService roleService;
    UserService userService;
    TeacherService teacherService;
    StudentService studentService;

    CourseService courseService;

    StudentGroupService studentGroupService;

    LectureMaterialService lectureMaterialService;

    TestService testService;

    QuestService questService;

    AnswerService answerService;

    CourseBlockService courseBlockService;

    ResultAnswerService resultAnswerService;

    HttpSessionBean httpSessionBean;

    @Autowired
    public TestController(UserService userService, RoleService roleService,
                          TeacherService teacherService, StudentService studentService,
                          StudentGroupService studentGroupService, CourseService courseService,
                          LectureMaterialService lectureMaterialService, TestService testService,
                          QuestService questService, AnswerService answerService,
                          CourseBlockService courseBlockService, HttpSessionBean httpSessionBean,
                          ResultAnswerService resultAnswerService
    ) {
        this.userService = userService;
        this.roleService = roleService;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.studentGroupService = studentGroupService;
        this.courseService = courseService;
        this.lectureMaterialService = lectureMaterialService;
        this.testService = testService;
        this.questService = questService;
        this.answerService = answerService;
        this.courseBlockService = courseBlockService;
        this.httpSessionBean = httpSessionBean;
        this.resultAnswerService = resultAnswerService;
    }


    @GetMapping("/create_test/{id}")
    public String createShowTest(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("newTest", new Test());
        model.addAttribute("courseBlock", courseBlockService.findOne(id));
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/test/create_test";
    }

    @PostMapping("/create_test/{id}")
    public String createTest(@PathVariable("id") int id, @ModelAttribute("newTest") Test testN, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        counterTest = 1;
        CourseBlock courseBlock = courseBlockService.findOne(id);
        Course course = courseService.findOne(courseBlock.getCourseCourseBlock().getId());
        Test test = new Test();
        test.setAttempt(testN.getAttempt());
        test.setName(testN.getName());
        test.setNumberOfQuest(testN.getNumberOfQuest());
        test.setNumberOfQuestInTest(testN.getNumberOfQuestInTest());
        test.setTime(testN.getTime());
        test.setTestToCourseBlock(courseBlock);
        test.setCourseTest(course);
        if (testService.showAll().size() == 0) {
            test.setIdentificator(1);
        } else {
            int max =testService.showAll().get(0).getId();

            for (int i = 1; i < testService.showAll().size(); i++) {
                if (testService.showAll().get(i).getId() > max) {
                    max = testService.showAll().get(i).getId();
                }
            }
            test.setIdentificator(max + 1);
        }
        testService.save(test);
        Test newTest = testService.findByfindByIdentificator(test.getIdentificator());
        if (courseBlock.getTests() == null) {
            courseBlock.setTests(Collections.singletonList(newTest));
        } else courseBlock.getTests().add(newTest);
        if (course.getTests() == null) {
            course.setTests(Collections.singletonList(newTest));
        } else course.getTests().add(newTest);

        courseBlockService.update(courseBlock.getId(), courseBlock);
        courseService.update(course.getId(), course);
        model.addAttribute("courseBlock", courseBlockService.findOne(id));
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/create_quest/" + newTest.getId();
    }


    @GetMapping("/create_quest/{id}")
    public String createShowQuest(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Quest quest = new Quest();
        Answer answerFalse1 = new Answer();
        Answer answerFalse2 = new Answer();
        Answer answerFalse3 = new Answer();
        Answer answerTrue = new Answer();
        CompleteTest completeTest = new CompleteTest(quest, answerFalse1, answerFalse2, answerFalse3, answerTrue);
        model.addAttribute("completeTest", completeTest);
        model.addAttribute("currentTest", testService.findOne(id));
        model.addAttribute("user", personDetails.getUser());
        return "pages/course/test/create_quest_to_test";
    }

    @PostMapping("/create_quest/{id}")
    public String createQuest(@PathVariable("id") int id,
                              @ModelAttribute("completeTest") CompleteTest completeTest,
                              Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("currentTest", testService.findOne(id));
        model.addAttribute("user", personDetails.getUser());

        Quest quest = completeTest.getQuest();
        Answer answerFalse1 = completeTest.getAnswerFalse1();
        Answer answerFalse2 = completeTest.getAnswerFalse2();
        Answer answerFalse3 = completeTest.getAnswerFalse3();
        Answer answerTrue = completeTest.getAnswerTrue();
        answerTrue.setIsTrue(1);


        Test test = testService.findOne(id);
        quest.setAnswers(new ArrayList<>(List.of(answerTrue, answerFalse1, answerFalse2, answerFalse3)));
        answerTrue.setQuest(quest);
        answerFalse1.setQuest(quest);
        answerFalse2.setQuest(quest);
        answerFalse3.setQuest(quest);
        if (test.getQuests() == null) {
            test.setQuests(Collections.singletonList(quest));
        } else test.getQuests().add(quest);
        quest.setTest(test);

        questService.save(quest);
        answerService.save(answerTrue);
        answerService.save(answerFalse1);
        answerService.save(answerFalse2);
        answerService.save(answerFalse3);
        testService.update(test.getId(), test);

        int numberOfQuestInTest = test.getNumberOfQuestInTest();
        if (numberOfQuestInTest <= counterTest) {
            return "redirect:/course_edit/" + test.getTestToCourseBlock().getCourseCourseBlock().getId();
        } else {
            counterTest++;
            return "redirect:/create_quest/" + id;
        }
    }

    @GetMapping("/test_edit/{id}")
    public String testEdit(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getUser());
        TestTake testTake = new TestTake();
        testTake.setCompleteTest(testTake.insertQuests(testService.findOne(id).getQuests()));
        model.addAttribute("testTake", testTake);
        model.addAttribute("testID", testService.findOne(id));
        return "pages/course/test/test_edit";
    }


    @PostMapping("/test_save")
    public String testButtonSave(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getUser());
        return "redirect:/welcome";
    }

    @PostMapping("/update_new_quest/{id}")
    public String testQuestAnswerSave(@PathVariable("id") int id, @ModelAttribute("testTake") TestTake testTake, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails.getUser());
        Quest quest = questService.findOne(id);
        Answer firstAnswer = answerService.findOne(testTake.getNewCompleteTestTT().getAnswerFalse1().getId());
        Answer secondAnswer = answerService.findOne(testTake.getNewCompleteTestTT().getAnswerFalse2().getId());
        Answer thirdAnswer = answerService.findOne(testTake.getNewCompleteTestTT().getAnswerFalse3().getId());
        Answer fourthAnswer = answerService.findOne(testTake.getNewCompleteTestTT().getAnswerTrue().getId());

        quest.setDescription(testTake.getNewCompleteTestTT().getQuest().getDescription());
        firstAnswer.setAnswer(testTake.getNewCompleteTestTT().getAnswerFalse1().getAnswer());
        secondAnswer.setAnswer(testTake.getNewCompleteTestTT().getAnswerFalse2().getAnswer());
        thirdAnswer.setAnswer(testTake.getNewCompleteTestTT().getAnswerFalse3().getAnswer());
        fourthAnswer.setAnswer(testTake.getNewCompleteTestTT().getAnswerTrue().getAnswer());

        questService.update(quest.getId(), quest);
        answerService.update(firstAnswer.getId(), firstAnswer);
        answerService.update(secondAnswer.getId(), secondAnswer);
        answerService.update(thirdAnswer.getId(), thirdAnswer);
        answerService.update(fourthAnswer.getId(), fourthAnswer);

        List<Answer> answerList = new ArrayList<>();
        answerList.add(firstAnswer);
        answerList.add(secondAnswer);
        answerList.add(thirdAnswer);
        answerList.add(fourthAnswer);

        List<ResultTest> test = quest.getTest().getResultTest();
        for (int i = 0; i < test.size(); i++) {
            for (int j = 0; j < test.get(i).getResultAnswers().size(); j++) {
                if (test.get(i).getResultAnswers().get(j).getQuest_id() == quest.getId()) {
                    if (test.get(i).getResultAnswers().get(j).getAnswerValueId() != 0) {
                        for (int i1 = 0; i1 < answerList.size(); i1++) {
                            if (answerList.get(i1).getId() == test.get(i).getResultAnswers().get(j).getAnswerValueId()) {
                                ResultAnswer resultAnswer = test.get(i).getResultAnswers().get(j);
                                resultAnswer.setAnswerIn(answerList.get(i1));
                                resultAnswer.setAnswerValueId(answerList.get(i1).getId());
                                resultAnswer.setCorectAnswer(answerList.get(i1).getIsTrue());
                                resultAnswerService.update(resultAnswer.getId(), resultAnswer);
                            }
                        }
                    }
                }
            }
        }
        return "redirect:/test_edit/" + quest.getTest().getId();
    }


}

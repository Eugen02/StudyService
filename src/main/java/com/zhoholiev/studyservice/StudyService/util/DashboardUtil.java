package com.zhoholiev.studyservice.StudyService.util;

import com.zhoholiev.studyservice.StudyService.models.*;
import com.zhoholiev.studyservice.StudyService.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DashboardUtil {

    int rating;

    int testNotTake = 0;
    Course courseInUtil;

    Student student;

    public DashboardUtil(Course courseInUtil, List<Test> resultTests, Student student) {
        this.courseInUtil = courseInUtil;
        this.student = student;
        this.rating = setRatingCustom(resultTests);
        checkFinishTest(resultTests);
    }

    private void checkFinishTest(List<Test> resultTests) {
        List<ResultTest> resultTestList = new ArrayList<>();
        for (int i = 0; i < resultTests.size(); i++) {
            resultTestList.addAll(resultTests.get(i).getResultTest());
        }
        List<ResultTest> resultTestsFinal = IntStream.range(0, resultTestList.size())
                .filter(i -> student.getId()
                        == resultTestList.get(i).getStudent().getId()).mapToObj(resultTestList::get)
                .toList();
        for (int i = 0; i < resultTestsFinal.size(); i++) {
            if (resultTestsFinal.get(i).getCurrentAttempt() == resultTestsFinal.get(i).getTestToResult().getAttempt()) {
                this.testNotTake++;
            }
        }
        this.testNotTake =  resultTests.size() - resultTestsFinal.size();
    }

    public int setRatingCustom(List<Test> tests) {
        List<ResultTest> resultTestList = new ArrayList<>();
        for (int i = 0; i < tests.size(); i++) {
            resultTestList.addAll(tests.get(i).getResultTest());
        }
        List<ResultTest> resultTestsFinal = IntStream.range(0, resultTestList.size())
                .filter(i -> student.getId()
                        == resultTestList.get(i).getStudent().getId()).mapToObj(resultTestList::get)
                .toList();
        double collect = resultTestsFinal.stream().mapToInt(ResultTest::getTestRating).sum();

        return (int) (collect / courseInUtil.getTests().size());
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Course getCourseInUtil() {
        return courseInUtil;
    }

    public void setCourseInUtil(Course courseInUtil) {
        this.courseInUtil = courseInUtil;
    }

    public int getTestNotTake() {
        return testNotTake;
    }

    public void setTestNotTake(int testNotTake) {
        this.testNotTake = testNotTake;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}

package com.zhoholiev.studyservice.StudyService.util.analise;

import com.zhoholiev.studyservice.StudyService.models.ResultTest;
import com.zhoholiev.studyservice.StudyService.models.Student;
import com.zhoholiev.studyservice.StudyService.models.StudentGroup;
import com.zhoholiev.studyservice.StudyService.models.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AnaliseTest {
    //Score
    private int lessF;
    private int betweenE;
    private int betweenD;
    private int betweenC;
    private int betweenB;
    private int betweenA;

    //Passage

    private int DidNotStart;
    private int DidNotPass;
    private int Passed;


    //Complexity of issues

    private List<Integer> counterNonTrueQuest = new ArrayList<>();

    private int width = 0;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private int allStudentsSize;

    //Best student

    ResultTest bestStudent;

    String time;

    // Synopsis

    String synopsisByScore;
    String synopsisByPassage;
    String synopsisByComplexityOfIssues;

    Test test;

    public AnaliseTest(Test test) {
        this.test = test;

        choseGrate();
        chosePassage();
        insertN();
        width = test.getQuests().size() * 30;
        findBestStudent();
        synopsis();
    }


    public AnaliseTest() {
    }


    void synopsis() {
        if (test.getResultTest().size() >= 3) {
            //1
            if (lessF + betweenE >= betweenA + betweenB + betweenC + betweenD) {
                synopsisByScore = "Ґрунтуючись на загальних результатах тест є дуже складним." +
                        System.lineSeparator() +
                        "Висновок: Варто змінити лекційний матеріал, додати більше інформації, змінити певні питання.";
            }
            if (lessF + betweenC + betweenD + betweenE <= betweenA + betweenB) {
                synopsisByScore = "Тест є досить слабким, оскільки всі результати мають високі оцінки" +
                        System.lineSeparator() +
                        "Висновок: Варто змінити/додати нові запитання, збільшити складність запитань";
            }
            if (lessF + betweenE <= betweenA + betweenC + betweenD + betweenB) {
                synopsisByScore = "Тест доволі збалансований, рівню складності відповідають як запитання так і лекційний матеріал, більшість оцінок позитивні та мають нормальний розподіл." +
                        System.lineSeparator() +
                        "Висновок: Тест створено добре, змінювати нічого не варто.";
            }
            //2

            if (DidNotStart >= DidNotPass + Passed) {
                synopsisByPassage = "Тест пройшло 50%  або менше студентів." +
                        System.lineSeparator() +
                        "Висновок: Аналіз тесту не об'єктивний, донесіть до студентів про необхідність пройти тест і лекційні матеріали.";
            }

            if (DidNotStart < DidNotPass + Passed) {
                synopsisByPassage = "Тест пройшла більша частина студентів" +
                        System.lineSeparator() +
                        "Висновок: Аналіз результатів буде максимально об'єктивний";
            }

            //3

            List<ResultTest> resultTestAll1 = test.getResultTest();
            List<Integer> resultFinally = new ArrayList<>();
            for (int i = 0; i < test.getQuests().size(); i++) {
                int template = 0;
                for (int j = 0; j < resultTestAll1.size(); j++) {
                    if (resultTestAll1.get(j).getResultAnswers().get(i).getCorectAnswer() == 0) {
                        template++;
                    }
                }
                resultFinally.add(template);
            }

            StringBuilder stringBuilder = new StringBuilder();
            int maxScore = Collections.max(resultFinally);
            resultFinally.remove(maxScore);
            int newMaxScore = Collections.max(resultFinally);
            int indexOfMax = 0;
            int maxValue = counterNonTrueQuest.get(0);
            for (int i = 0; i < counterNonTrueQuest.size(); i++) {
                if (counterNonTrueQuest.get(i) > counterNonTrueQuest.get(indexOfMax)) {
                    indexOfMax = i;
                    maxValue=counterNonTrueQuest.get(i);
                }
            }
            for (int i = 0; i < counterNonTrueQuest.size(); i++){
                if (counterNonTrueQuest.get(i) == maxValue){
                    stringBuilder.append(i+1).append(", ");
                }
            }


            if (maxScore - newMaxScore < (test.getResultTest().size() / 2)) {
                synopsisByComplexityOfIssues = "Найскладніше запитання: " + stringBuilder.toString() + " усі запитання збалансовані, запитання і відповіді складені правильно, труднощів у студентів не викликало." +
                        System.lineSeparator() +
                        "Висновок: Змінювати запитання непотрібно.";
            }


            if (maxScore - newMaxScore > (test.getResultTest().size() / 2)) {
                synopsisByComplexityOfIssues = "Запитання: " + stringBuilder.toString() + " є з високою кількістю неправильних відповідей, усі інші запитання збалансовані." +
                        System.lineSeparator() +
                        "Висновок: Перегляньте питання:" + stringBuilder.toString() + " змініть постановку питання, або варіанти відповіді, так само перегляньте чи є інформація про це питання в лекційному матеріалі.";
            }


        } else {
            synopsisByScore = "Мало результатів проходження для аналізу даних, почекайте поки більше студентів пройдуть тест.";
            synopsisByPassage = "Мало результатів проходження для аналізу даних, почекайте поки більше студентів пройдуть тест.";
            synopsisByComplexityOfIssues = "Мало результатів проходження для аналізу даних, почекайте поки більше студентів пройдуть тест.";
        }
    }

    void findBestStudent() {
        if (test.getResultTest().size() != 0) {
            bestStudent = test.getResultTest().get(0);
            int transitTime = test.getResultTest().get(0).getTransitTime();
            String as = String.valueOf(transitTime / 60);
            String a = String.valueOf(transitTime - (60 * (transitTime / 60)));
            time = ((as.length() < 2) ? ('0' + as) : as) +
                    ":" +
                    ((a.length() < 2) ? ('0' + a) : a);

            for (int i = 0; i < test.getResultTest().size(); i++) {
                if (test.getResultTest().get(i).getTestRating() > bestStudent.getTestRating()) {
                    bestStudent = test.getResultTest().get(i);

                    transitTime = test.getResultTest().get(i).getTransitTime();
                    as = String.valueOf(transitTime / 60);
                    a = String.valueOf(transitTime - (60 * (transitTime / 60)));
                    time = ((as.length() < 2) ? ('0' + as) : as) +
                            ":" +
                            ((a.length() < 2) ? ('0' + a) : a);


                }
            }
        }
    }

    void insertN() {
        List<ResultTest> resultTestAll = test.getResultTest();
        for (int i = 0; i < test.getQuests().size(); i++) {
            int template = 0;
            for (int j = 0; j < resultTestAll.size(); j++) {
                if (resultTestAll.get(j).getResultAnswers().get(i).getCorectAnswer() == 0) {
                    template++;
                }
            }
            counterNonTrueQuest.add(template);
        }
    }

    void chosePassage() {
        List<ResultTest> resultTestAll = test.getResultTest();
        for (int i = 0; i < resultTestAll.size(); i++) {
            if (resultTestAll.get(i).getTestRating() < 60) {
                this.DidNotPass++;
            }
            if (resultTestAll.get(i).getTestRating() >= 60) {
                this.Passed++;
            }
        }
        List<StudentGroup> studentGroups = test.getCourseTest().getStudentGroups();
        int allStudents = 0;
        for (StudentGroup studentGroup : studentGroups) {
            allStudents += studentGroup.getStudents().size();
        }
        allStudentsSize = allStudents;
        DidNotStart = allStudents - (DidNotPass + Passed);
    }


    void choseGrate() {
        List<ResultTest> resultTestAll = test.getResultTest();
        for (int i = 0; i < resultTestAll.size(); i++) {
            if (resultTestAll.get(i).getTestRating() < 60) {
                this.lessF++;
            }

            if (resultTestAll.get(i).getTestRating() >= 60 &&
                    resultTestAll.get(i).getTestRating() < 64) {
                this.betweenE++;
            }

            if (resultTestAll.get(i).getTestRating() >= 64 &&
                    resultTestAll.get(i).getTestRating() < 75) {
                this.betweenD++;
            }


            if (resultTestAll.get(i).getTestRating() >= 75 &&
                    resultTestAll.get(i).getTestRating() < 82) {
                this.betweenC++;
            }


            if (resultTestAll.get(i).getTestRating() >= 82 &&
                    resultTestAll.get(i).getTestRating() < 90) {
                this.betweenB++;
            }


            if (resultTestAll.get(i).getTestRating() >= 90 &&
                    resultTestAll.get(i).getTestRating() <= 100) {
                this.betweenA++;
            }


        }
    }

    public int getAllStudentsSize() {
        return allStudentsSize;
    }

    public void setAllStudentsSize(int allStudentsSize) {
        this.allStudentsSize = allStudentsSize;
    }

    public String getSynopsisByScore() {
        return synopsisByScore;
    }

    public void setSynopsisByScore(String synopsisByScore) {
        this.synopsisByScore = synopsisByScore;
    }

    public String getSynopsisByPassage() {
        return synopsisByPassage;
    }

    public void setSynopsisByPassage(String synopsisByPassage) {
        this.synopsisByPassage = synopsisByPassage;
    }

    public String getSynopsisByComplexityOfIssues() {
        return synopsisByComplexityOfIssues;
    }

    public void setSynopsisByComplexityOfIssues(String synopsisByComplexityOfIssues) {
        this.synopsisByComplexityOfIssues = synopsisByComplexityOfIssues;
    }

    public ResultTest getBestStudent() {
        return bestStudent;
    }

    public void setBestStudent(ResultTest bestStudent) {
        this.bestStudent = bestStudent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Integer> getCounterNonTrueQuest() {
        return counterNonTrueQuest;
    }

    public void setCounterNonTrueQuest(List<Integer> counterNonTrueQuest) {
        this.counterNonTrueQuest = counterNonTrueQuest;
    }

    public int getDidNotStart() {
        return DidNotStart;
    }

    public void setDidNotStart(int didNotStart) {
        DidNotStart = didNotStart;
    }

    public int getDidNotPass() {
        return DidNotPass;
    }

    public void setDidNotPass(int didNotPass) {
        DidNotPass = didNotPass;
    }

    public int getPassed() {
        return Passed;
    }

    public void setPassed(int passed) {
        Passed = passed;
    }

    public int getLessF() {
        return lessF;
    }

    public void setLessF(int lessF) {
        this.lessF = lessF;
    }

    public int getBetweenE() {
        return betweenE;
    }

    public void setBetweenE(int betweenE) {
        this.betweenE = betweenE;
    }

    public int getBetweenD() {
        return betweenD;
    }

    public void setBetweenD(int betweenD) {
        this.betweenD = betweenD;
    }

    public int getBetweenC() {
        return betweenC;
    }

    public void setBetweenC(int betweenC) {
        this.betweenC = betweenC;
    }

    public int getBetweenB() {
        return betweenB;
    }

    public void setBetweenB(int betweenB) {
        this.betweenB = betweenB;
    }

    public int getBetweenA() {
        return betweenA;
    }

    public void setBetweenA(int betweenA) {
        this.betweenA = betweenA;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}

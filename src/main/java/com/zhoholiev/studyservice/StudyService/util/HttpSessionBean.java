package com.zhoholiev.studyservice.StudyService.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Date;
import java.util.List;

@SessionScope
@Component
public class HttpSessionBean {

   private TestTake testTake;

private Date startData;
private Date endData;
private int transitTime;
    public TestTake getTestTake() {
        return testTake;
    }

    public void setTestTake(TestTake testTake) {
        this.testTake = testTake;
    }

    public int getTransitTime() {
        return transitTime;
    }

    public void setTransitTime(int transitData) {
        this.transitTime = transitData;
    }

    public Date getEndData() {
        return endData;
    }

    public void setEndData(Date endData) {
        this.endData = endData;
    }

    public Date getStartData() {
        return startData;
    }

    public void setStartData(Date startData) {
        this.startData = startData;
    }

    @Override
    public String toString() {
        return "HttpSessionBean{" +
                "testTake=" + testTake +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpSessionBean that = (HttpSessionBean) o;
        return testTake != null ? testTake.equals(that.testTake) : that.testTake == null;
    }

    @Override
    public int hashCode() {
        return testTake != null ? testTake.hashCode() : 0;
    }

    public void killTestTake() {
        this.testTake = null;
    }
}

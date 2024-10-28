package com.example.eng2utc.Model;

public class Test {
    private String nameOfTest;
    private String testId;
    private String testTypeId;

    public Test() {}

    public Test(String nameOfTest, String testId, String testTypeId) {
        this.nameOfTest = nameOfTest;
        this.testId = testId;
        this.testTypeId = testTypeId;
    }

    public String getNameOfTest() {
        return nameOfTest;
    }

    public void setNameOfTest(String nameOfTest) {
        this.nameOfTest = nameOfTest;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestTypeId() {
        return testTypeId;
    }

    public void setTestTypeId(String testTypeId) {
        this.testTypeId = testTypeId;
    }
}

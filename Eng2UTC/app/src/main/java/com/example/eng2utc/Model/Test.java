package com.example.eng2utc.Model;

public class Test {
    private String NAME_OF_TEST;
    private String TEST_ID;
    private String TEST_TYPE_ID;

    public Test() {}

    public Test(String nameOfTest, String testId, String testTypeId) {
        this.NAME_OF_TEST = nameOfTest;
        this.TEST_ID = testId;
        this.TEST_TYPE_ID = testTypeId;
    }

    public String getNAME_OF_TEST() {
        return NAME_OF_TEST;
    }

    public void setNAME_OF_TEST(String NAME_OF_TEST) {
        this.NAME_OF_TEST = NAME_OF_TEST;
    }

    public String getTEST_ID() {
        return TEST_ID;
    }

    public void setTEST_ID(String TEST_ID) {
        this.TEST_ID = TEST_ID;
    }

    public String getTEST_TYPE_ID() {
        return TEST_TYPE_ID;
    }

    public void setTEST_TYPE_ID(String TEST_TYPE_ID) {
        this.TEST_TYPE_ID = TEST_TYPE_ID;
    }
}

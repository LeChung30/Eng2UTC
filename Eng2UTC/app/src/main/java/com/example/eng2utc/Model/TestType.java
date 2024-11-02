package com.example.eng2utc.Model;

public class TestType {
    private String CERT_LEVEL_ID, CERT_LEVEL_NAME;
    private int MAXIMUM_SCORE;
    private String NAME_OF_TYPE_TEST, TEST_TYPE_ID;
    private int TOTAL_DURATION;

    public TestType() {}

    public TestType(String CERT_LEVEL_ID, String CERT_LEVEL_NAME, int MAXIMUM_SCORE, String NAME_OF_TYPE_TEST, String TEST_TYPE_ID, int TOTAL_DURATION) {
        this.CERT_LEVEL_ID = CERT_LEVEL_ID;
        this.CERT_LEVEL_NAME = CERT_LEVEL_NAME;
        this.MAXIMUM_SCORE = MAXIMUM_SCORE;
        this.NAME_OF_TYPE_TEST = NAME_OF_TYPE_TEST;
        this.TEST_TYPE_ID = TEST_TYPE_ID;
        this.TOTAL_DURATION = TOTAL_DURATION;
    }

    public String getCERT_LEVEL_ID() {
        return CERT_LEVEL_ID;
    }

    public void setCERT_LEVEL_ID(String CERT_LEVEL_ID) {
        this.CERT_LEVEL_ID = CERT_LEVEL_ID;
    }

    public String getCERT_LEVEL_NAME() {
        return CERT_LEVEL_NAME;
    }

    public void setCERT_LEVEL_NAME(String CERT_LEVEL_NAME) {
        this.CERT_LEVEL_NAME = CERT_LEVEL_NAME;
    }

    public int getMAXIMUM_SCORE() {
        return MAXIMUM_SCORE;
    }

    public void setMAXIMUM_SCORE(int MAXIMUM_SCORE) {
        this.MAXIMUM_SCORE = MAXIMUM_SCORE;
    }

    public String getNAME_OF_TYPE_TEST() {
        return NAME_OF_TYPE_TEST;
    }

    public void setNAME_OF_TYPE_TEST(String NAME_OF_TYPE_TEST) {
        this.NAME_OF_TYPE_TEST = NAME_OF_TYPE_TEST;
    }

    public String getTEST_TYPE_ID() {
        return TEST_TYPE_ID;
    }

    public void setTEST_TYPE_ID(String TEST_TYPE_ID) {
        this.TEST_TYPE_ID = TEST_TYPE_ID;
    }

    public int getTOTAL_DURATION() {
        return TOTAL_DURATION;
    }

    public void setTOTAL_DURATION(int TOTAL_DURATION) {
        this.TOTAL_DURATION = TOTAL_DURATION;
    }
}

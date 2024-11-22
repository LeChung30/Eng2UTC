package com.example.eng2utc.Model;

public class UserAnswer {
    private String USER_ANSWER_ID;
    private String USER_ID;
    private String TEST_ID;
    private String QUESTION_ID;
    private String ANSWER_ID;
    private String RAW_USER_ANSWER;
    private String USER_NOTE;
    private String USER_TEST_ID;

    public UserAnswer() {}

    public UserAnswer(String USER_ANSWER_ID, String USER_ID, String TEST_ID, String QUESTION_ID, String ANSWER_ID, String RAW_USER_ANSWER, String USER_NOTE, String USER_TEST_ID) {
        this.USER_ANSWER_ID = USER_ANSWER_ID;
        this.USER_ID = USER_ID;
        this.TEST_ID = TEST_ID;
        this.QUESTION_ID = QUESTION_ID;
        this.ANSWER_ID = ANSWER_ID;
        this.RAW_USER_ANSWER = RAW_USER_ANSWER;
        this.USER_NOTE = USER_NOTE;
        this.USER_TEST_ID = USER_TEST_ID;
    }

    public String getUSER_ANSWER_ID() {
        return USER_ANSWER_ID;
    }

    public void setUSER_ANSWER_ID(String USER_ANSWER_ID) {
        this.USER_ANSWER_ID = USER_ANSWER_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getTEST_ID() {
        return TEST_ID;
    }

    public void setTEST_ID(String TEST_ID) {
        this.TEST_ID = TEST_ID;
    }

    public String getQUESTION_ID() {
        return QUESTION_ID;
    }

    public void setQUESTION_ID(String QUESTION_ID) {
        this.QUESTION_ID = QUESTION_ID;
    }

    public String getANSWER_ID() {
        return ANSWER_ID;
    }

    public void setANSWER_ID(String ANSWER_ID) {
        this.ANSWER_ID = ANSWER_ID;
    }

    public String getRAW_USER_ANSWER() {
        return RAW_USER_ANSWER;
    }

    public void setRAW_USER_ANSWER(String RAW_USER_ANSWER) {
        this.RAW_USER_ANSWER = RAW_USER_ANSWER;
    }

    public String getUSER_NOTE() {
        return USER_NOTE;
    }

    public void setUSER_NOTE(String USER_NOTE) {
        this.USER_NOTE = USER_NOTE;
    }

    public String getUSER_TEST_ID() {
        return USER_TEST_ID;
    }

    public void setUSER_TEST_ID(String USER_TEST_ID) {
        this.USER_TEST_ID = USER_TEST_ID;
    }
}
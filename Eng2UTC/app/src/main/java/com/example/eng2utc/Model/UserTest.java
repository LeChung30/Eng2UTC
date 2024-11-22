package com.example.eng2utc.Model;

import com.google.firebase.database.PropertyName;

public class UserTest {
    @PropertyName("USER_TEST_ID")
    private String USER_TEST_ID;

    @PropertyName("USER_ID")
    private String USER_ID;

    @PropertyName("TEST_ID")
    private String TEST_ID;

    @PropertyName("TEST_DATE")
    private String TEST_DATE;

    @PropertyName("DURATION")
    private String DURATION;

    @PropertyName("SCORE")
    private int SCORE;

    // Constructor
    public UserTest() {}

    public UserTest(String USER_TEST_ID, String USER_ID, String TEST_ID, String TEST_DATE, String DURATION, int SCORE) {
        this.USER_TEST_ID = USER_TEST_ID;
        this.USER_ID = USER_ID;
        this.TEST_ID = TEST_ID;
        this.TEST_DATE = TEST_DATE;
        this.DURATION = DURATION;
        this.SCORE = SCORE;
    }

    // Getter và Setter với @PropertyName
    @PropertyName("USER_TEST_ID")
    public String getUSER_TEST_ID() {
        return USER_TEST_ID;
    }

    @PropertyName("USER_TEST_ID")
    public void setUSER_TEST_ID(String USER_TEST_ID) {
        this.USER_TEST_ID = USER_TEST_ID;
    }

    @PropertyName("USER_ID")
    public String getUSER_ID() {
        return USER_ID;
    }

    @PropertyName("USER_ID")
    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    @PropertyName("TEST_ID")
    public String getTEST_ID() {
        return TEST_ID;
    }

    @PropertyName("TEST_ID")
    public void setTEST_ID(String TEST_ID) {
        this.TEST_ID = TEST_ID;
    }

    @PropertyName("TEST_DATE")
    public String getTEST_DATE() {
        return TEST_DATE;
    }

    @PropertyName("TEST_DATE")
    public void setTEST_DATE(String TEST_DATE) {
        this.TEST_DATE = TEST_DATE;
    }

    @PropertyName("DURATION")
    public String getDURATION() {
        return DURATION;
    }

    @PropertyName("DURATION")
    public void setDURATION(String DURATION) {
        this.DURATION = DURATION;
    }

    @PropertyName("SCORE")
    public int getSCORE() {
        return SCORE;
    }

    @PropertyName("SCORE")
    public void setSCORE(int SCORE) {
        this.SCORE = SCORE;
    }
}

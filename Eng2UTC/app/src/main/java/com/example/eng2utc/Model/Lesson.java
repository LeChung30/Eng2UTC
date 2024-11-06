package com.example.eng2utc.Model;

public class Lesson {
    private String CERT_LEVEL_ID;
    private String CERT_LEVEL_NAME;
    private String IMAGE_LINK;
    private Integer IS_VOCAB;
    private String LESSON_ID;
    private String NAME_OF_LESSON;
    private Integer ORDER;
    private String TOPIC_ID;
    private String TOPIC_NAME;

    public Lesson() {
    }

    public Lesson(String CERT_LEVEL_ID, String CERT_LEVEL_NAME, String IMAGE_LINK, Integer IS_VOCAB, String LESSON_ID, String NAME_OF_LESSON, Integer ORDER, String TOPIC_ID, String TOPIC_NAME) {
        this.CERT_LEVEL_ID = CERT_LEVEL_ID;
        this.CERT_LEVEL_NAME = CERT_LEVEL_NAME;
        this.IMAGE_LINK = IMAGE_LINK;
        this.IS_VOCAB = IS_VOCAB;
        this.LESSON_ID = LESSON_ID;
        this.NAME_OF_LESSON = NAME_OF_LESSON;
        this.ORDER = ORDER;
        this.TOPIC_ID = TOPIC_ID;
        this.TOPIC_NAME = TOPIC_NAME;
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

    public String getIMAGE_LINK() {
        return IMAGE_LINK;
    }

    public void setIMAGE_LINK(String IMAGE_LINK) {
        this.IMAGE_LINK = IMAGE_LINK;
    }

    public Integer getIS_VOCAB() {
        return IS_VOCAB;
    }

    public void setIS_VOCAB(Integer IS_VOCAB) {
        this.IS_VOCAB = IS_VOCAB;
    }

    public String getLESSON_ID() {
        return LESSON_ID;
    }

    public void setLESSON_ID(String LESSON_ID) {
        this.LESSON_ID = LESSON_ID;
    }

    public String getNAME_OF_LESSON() {
        return NAME_OF_LESSON;
    }

    public void setNAME_OF_LESSON(String NAME_OF_LESSON) {
        this.NAME_OF_LESSON = NAME_OF_LESSON;
    }

    public Integer getORDER() {
        return ORDER;
    }

    public void setORDER(Integer ORDER) {
        this.ORDER = ORDER;
    }

    public String getTOPIC_ID() {
        return TOPIC_ID;
    }

    public void setTOPIC_ID(String TOPIC_ID) {
        this.TOPIC_ID = TOPIC_ID;
    }

    public String getTOPIC_NAME() {
        return TOPIC_NAME;
    }

    public void setTOPIC_NAME(String TOPIC_NAME) {
        this.TOPIC_NAME = TOPIC_NAME;
    }
}

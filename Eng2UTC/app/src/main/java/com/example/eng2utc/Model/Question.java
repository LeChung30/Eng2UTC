package com.example.eng2utc.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {
    @SerializedName("QUESTION_ID")
    private String QUESTION_ID;

    @SerializedName("CONTENT")
    private String content;

    @SerializedName("CORRECT_ANSWER_ID")
    private String correctAnswerId;

    @SerializedName("IMAGE_LINK")
    private String imageLink;

    @SerializedName("ORDER")
    private int order;

    @SerializedName("ANSWERS")
    private List<Answer> answers;

    public Question() {
    }

public Question(String QUESTION_ID,String content, String correctAnswerId, String imageLink, int order, List<Answer> answers) {
        this.QUESTION_ID = QUESTION_ID;
        this.content = content;
        this.correctAnswerId = correctAnswerId;
        this.imageLink = imageLink;
        this.order = order;
        this.answers = answers;
    }

    public String getQUESTION_ID() {
        return QUESTION_ID;
    }

    public void setQUESTION_ID(String QUESTION_ID) {
        this.QUESTION_ID = QUESTION_ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCorrectAnswerId() {
        return correctAnswerId;
    }

    public void setCorrectAnswerId(String correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
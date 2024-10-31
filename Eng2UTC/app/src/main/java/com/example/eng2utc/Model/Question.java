package com.example.eng2utc.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {
    @SerializedName("CONTENT")
    private String content;

    @SerializedName("CORRECT_ANSWER_ID")
    private String correctAnswerId;

    @SerializedName("IMAGE_LINK")
    private String imageLink;

    @SerializedName("ANSWERS")
    private List<Answer> answers;

    public Question() {
    }

    public Question(String content, String correctAnswerId, String imageLink, List<Answer> answers) {
        this.content = content;
        this.correctAnswerId = correctAnswerId;
        this.imageLink = imageLink;
        this.answers = answers;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
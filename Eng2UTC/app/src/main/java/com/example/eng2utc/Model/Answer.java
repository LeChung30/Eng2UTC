package com.example.eng2utc.Model;

import com.google.gson.annotations.SerializedName;

public class Answer {
    @SerializedName("ANSWER_ID")
    private String answerId;

    @SerializedName("CONTENT")
    private String content;

    @SerializedName("IMAGE_LINK")
    private String imageLink;

    public Answer() {
    }

    public Answer(String answerId, String content, String imageLink) {
        this.answerId = answerId;
        this.content = content;
        this.imageLink = imageLink;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}

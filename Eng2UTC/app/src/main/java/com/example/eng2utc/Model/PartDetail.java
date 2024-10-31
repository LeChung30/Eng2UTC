package com.example.eng2utc.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PartDetail implements Serializable {
    @SerializedName("AUDIO_LINK")
    private String audioLink;

    @SerializedName("CONTENT")
    private String content;

    @SerializedName("ORDER")
    private int order;

    @SerializedName("PART_DETAIL_ID")
    private String partDetailId;

    @SerializedName("PART_OF_TEST_ID")
    private String partOfTestId;

    @SerializedName("questions")
    private List<Question> questions;

    public PartDetail() {
    }

    public PartDetail(String partDetailId, String audioLink, String content, int order, String partOfTestId, List<Question> questions) {
        this.audioLink = audioLink;
        this.content = content;
        this.order = order;
        this.partDetailId = partDetailId;
        this.partOfTestId = partOfTestId;
        this.questions = questions;
    }

    public String getAudioLink() {
        return audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPartDetailId() {
        return partDetailId;
    }

    public void setPartDetailId(String partDetailId) {
        this.partDetailId = partDetailId;
    }

    public String getPartOfTestId() {
        return partOfTestId;
    }

    public void setPartOfTestId(String partOfTestId) {
        this.partOfTestId = partOfTestId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

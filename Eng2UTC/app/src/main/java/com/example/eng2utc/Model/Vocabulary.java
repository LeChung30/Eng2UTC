package com.example.eng2utc.Model;

import com.example.eng2utc.Model.CertLevel;

import java.io.Serializable;
import java.util.Map;

public class Vocabulary implements Serializable {
    private String VOCAB_ID;
    private String WORD;
    private String PRONUNCIATION;
    private String PART_OF_SPEECH;
    private String MEANING;
    private String AUDIO_LINK;
    private String IMAGE_LINK;
    private Map<String, CertLevel> CERT_LEVEL_ID;

    // Constructor mặc định
    public Vocabulary() {
    }

    // Getters và Setters cho các thuộc tính
    public String getVOCAB_ID() {
        return VOCAB_ID;
    }

    public void setVOCAB_ID(String VOCAB_ID) {
        this.VOCAB_ID = VOCAB_ID;
    }

    public String getWORD() {
        return WORD;
    }

    public void setWORD(String WORD) {
        this.WORD = WORD;
    }

    public String getPRONUNCIATION() {
        return PRONUNCIATION;
    }

    public void setPRONUNCIATION(String PRONUNCIATION) {
        this.PRONUNCIATION = PRONUNCIATION;
    }

    public String getPART_OF_SPEECH() {
        return PART_OF_SPEECH;
    }

    public void setPART_OF_SPEECH(String PART_OF_SPEECH) {
        this.PART_OF_SPEECH = PART_OF_SPEECH;
    }

    public String getMEANING() {
        return MEANING;
    }

    public void setMEANING(String MEANING) {
        this.MEANING = MEANING;
    }

    public String getAUDIO_LINK() {
        return AUDIO_LINK;
    }

    public void setAUDIO_LINK(String AUDIO_LINK) {
        this.AUDIO_LINK = AUDIO_LINK;
    }

    public String getIMAGE_LINK() {
        return IMAGE_LINK;
    }

    public void setIMAGE_LINK(String IMAGE_LINK) {
        this.IMAGE_LINK = IMAGE_LINK;
    }

    // Phương thức để lấy CERT_LEVEL_ID
    public Map<String, CertLevel> getCertLevelId() {
        return CERT_LEVEL_ID;
    }

    public void setCertLevelId(Map<String, CertLevel> CERT_LEVEL_ID) {
        this.CERT_LEVEL_ID = CERT_LEVEL_ID;
    }
}

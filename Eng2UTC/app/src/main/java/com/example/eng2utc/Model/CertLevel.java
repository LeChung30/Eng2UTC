package com.example.eng2utc.Model;

public class CertLevel {
    private String CERT_LEVEL_ID;
    private String CERT_LEVEL_NAME;
    private String DESCRIPTION;
    private String IMAGE_LINK;

    // Constructor mặc định
    public CertLevel() {
    }

    // Getters và Setters cho các thuộc tính
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

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getIMAGE_LINK() {
        return IMAGE_LINK;
    }

    public void setIMAGE_LINK(String IMAGE_LINK) {
        this.IMAGE_LINK = IMAGE_LINK;
    }
}


package com.example.eng2utc.Model;

import com.google.gson.annotations.SerializedName;

public class MarkOfTest {
    @SerializedName("SCORE")
    private int mark;
    @SerializedName("QUANTITY")
    private int quantity;
    private String label;

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLabel() {
        return mark+""; // convert int to string
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

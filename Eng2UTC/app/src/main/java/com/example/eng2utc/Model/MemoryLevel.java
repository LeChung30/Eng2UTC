package com.example.eng2utc.Model;

import com.google.gson.annotations.SerializedName;

public class MemoryLevel {
    @SerializedName("MEMORY_LEVEL")
    private int level;
    @SerializedName("QUANTITY")
    private int count;
    private String label;


    // Getters and setters
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLabel() {
        label = "Level " + level;
        return label;
    }
}

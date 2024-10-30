package com.example.eng2utc.Model;

import java.util.List;

public class PartDetailResponse {
    private List<PartDetail> part_details;

    // Getter và Setter
    public List<PartDetail> getPartDetails() {
        return part_details;
    }

    public void setPartDetails(List<PartDetail> partDetails) {
        this.part_details = partDetails;
    }
}


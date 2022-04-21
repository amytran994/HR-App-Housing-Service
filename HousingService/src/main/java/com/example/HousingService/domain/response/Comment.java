package com.example.HousingService.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String comment;
    private String createdBy;
    private String employeeId;
    private String commentedDate;
}

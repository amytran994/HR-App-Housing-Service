package com.example.HousingService.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDetail {
    private Integer reportId;
    private String title;
    private String description;
    private String createdBy;
    private String employeeId;
    private String reportedDate;
    private String status; // open, in progress, closed
    private List<Comment> comments; // each employee can create one comment

}

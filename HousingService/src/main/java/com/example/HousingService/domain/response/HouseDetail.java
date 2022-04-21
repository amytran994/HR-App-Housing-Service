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
public class HouseDetail {
    private String address;
    private String landlordFullname;
    private String phone;
    private String email;

    private int numOfBed;
    private int numOfMattress;
    private int numOfTable;
    private int numOfChair;

    List<ReportItem> reports;
}

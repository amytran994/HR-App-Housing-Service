package com.example.HousingService.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table
public class FacilityReportDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FacilityReportID")
    private FacilityReport facilityReport;

    @Column(name = "EmployeeID")
    private String employeeId;

    @Column(name = "Comment")
    private String comment;

    @Column(name = "CreateDate")
    private String createDate;

    @Column(name = "LastModificationDate")
    private String lastModificationDate;

}

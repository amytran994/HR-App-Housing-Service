package com.example.HousingService.domain.request;

import com.example.HousingService.domain.entity.Facility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFacilityRequest {
    List<Facility> facilityList;

}

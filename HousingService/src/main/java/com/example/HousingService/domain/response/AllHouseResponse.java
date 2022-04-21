package com.example.HousingService.domain.response;

import com.example.HousingService.domain.entity.House;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllHouseResponse {
    private List<HouseItem> houses;
}

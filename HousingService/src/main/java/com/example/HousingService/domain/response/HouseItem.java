package com.example.HousingService.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseItem {
    private String address;
    private String landlordFullname;
    private String phone;
    private String email;
    private int maxOccupant;
}

package com.example.HousingService.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddHouseRequest {
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String address;
    private int maxOccupant;
}

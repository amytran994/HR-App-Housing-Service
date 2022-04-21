package com.example.HousingService.service;

import com.example.HousingService.dao.HousingDao;
import com.example.HousingService.domain.entity.House;
import com.example.HousingService.domain.request.AddHouseRequest;
import com.example.HousingService.domain.response.AllHouseResponse;
import com.example.HousingService.domain.response.Comment;
import com.example.HousingService.domain.response.HouseDetail;
import com.example.HousingService.domain.response.ReportDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class HousingService {

    private HousingDao housingDao;

    @Autowired
    public void setHousingDao(HousingDao housingDao) {
        this.housingDao = housingDao;
    }

    /* For HR */

    // View all houses
    // return AllHouseResponse
    @GetMapping("house")
    public AllHouseResponse getAllHouses() {

        return null;
    }

    // Add new house
    // return House
    @PostMapping("house/add")
    public House addHouse(AddHouseRequest house) {
        return null;
    }

    // Delete a house
    // return message
    @DeleteMapping("house/{houseId}")
    public int deleteHouse() {

        return 1;
    }

    // View House record (house info, landlord info, facility info, reports)
    // reports sorted in desc order of date
    // return HouseDetail
    @GetMapping("house/{houseId}")
    public HouseDetail getHouse(Integer houseId) {

        return null;
    }


    /* For Employee */

    // composite: find employeeId
    // view house
    @GetMapping("house/employee/{employeeId}")
    public HouseDetail getEmployeeHouse(String employeeId) {
        return null;
    }

    // composite
    // Add report
    @PostMapping("report/{facilityId}/{employeeId}")
    public ReportDetail report(Integer facilityId, String employeeId) {
        return null;
    }

    // Get all comments about this report
    public List<Comment> getAllComments() {

        return null;
    }

    /* For All
     */

    // Add comment on report
    // Create new comment related to this report
    // return comment
    public Comment addComment() {
        return null;
    }

    // Find comment of this employee. Update comment
    // return comment
    public Comment updateComment() {
        return null;
    }

}

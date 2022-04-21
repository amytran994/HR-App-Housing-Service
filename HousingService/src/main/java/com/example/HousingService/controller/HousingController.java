package com.example.HousingService.controller;

import com.example.HousingService.dao.HousingDao;
import com.example.HousingService.domain.entity.Facility;
import com.example.HousingService.domain.entity.FacilityReport;
import com.example.HousingService.domain.entity.FacilityReportDetail;
import com.example.HousingService.domain.entity.House;
import com.example.HousingService.domain.request.AddFacilityRequest;
import com.example.HousingService.domain.request.AddHouseRequest;
import com.example.HousingService.domain.response.AllHouseResponse;
import com.example.HousingService.domain.response.ReportDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("housing")
public class HousingController {

    private HousingDao housingDao;

    @Autowired
    public void setHousingDao(HousingDao housingDao) {
        this.housingDao = housingDao;
    }

    /* For HR */

    // View all houses
    // return AllHouseResponse
    @GetMapping("house")
    public ResponseEntity getAllHouses() {
        return new ResponseEntity(housingDao.getAllHouses(), HttpStatus.OK);
    }

    // Add new house
    // return House
    @PostMapping("house/add")
    public ResponseEntity addHouse(@RequestBody AddHouseRequest house) {
        return new ResponseEntity(housingDao.addHouse(house), HttpStatus.OK);
    }

    // Add a list of facilites to this house
    @PostMapping("house/{houseId}/facility")
    public ResponseEntity addFacility(@PathVariable Integer houseId, @RequestBody AddFacilityRequest request) {

        return new ResponseEntity(housingDao.addFacility(houseId, request) + " added", HttpStatus.OK);
    }

    // update a facility
    @PutMapping("house/facility/{facilityId}")
    public ResponseEntity updateFacility(@PathVariable Integer facilityId, @RequestBody Facility request) {
        return new ResponseEntity(housingDao.updateFacility(request, facilityId), HttpStatus.OK);
    }

    // Delete a house
    // return message
    @DeleteMapping("house/{houseId}")
    public ResponseEntity deleteHouse(@PathVariable Integer houseId) {
        return new ResponseEntity(housingDao.deleteHouse(houseId), HttpStatus.OK);
    }

    // composite
    // View House record (house info, landlord info, facility info, reports)
    // reports sorted in desc order of date
    // return HouseDetail
    @GetMapping("house/{houseId}")
    public ResponseEntity getHouse(@PathVariable Integer houseId) {
        return new ResponseEntity(housingDao.getHouse(houseId), HttpStatus.OK);
    }


    /* For Employee */

    // composite: find houseId of this employee from employee service
    // get house of this employee
    @GetMapping("house/employee/{houseId}")
    public ResponseEntity getEmployeeHouse(@PathVariable String houseId) {
        return new ResponseEntity(housingDao.getEmployeeHouse(houseId), HttpStatus.OK);
    }

    // composite
    // Add report
    // Assume that employeeId is added into report by composite service
    @PostMapping("report/{facilityId}")
    public ResponseEntity report(@PathVariable Integer facilityId,
                                 @RequestBody ReportDetail request) {

        return new ResponseEntity(housingDao.report(facilityId, request), HttpStatus.OK);
    }

    // composite
    // Get all comments about this report
    @GetMapping("report/{reportId}")
    public ResponseEntity getAllComments(@PathVariable Integer reportId) {
        return new ResponseEntity(housingDao.getAllComments(reportId), HttpStatus.OK);
    }

    /* For All
     */

    // composite
    // Add comment on report
    // Create new comment related to this report
    // return comment
    @PostMapping("comment/{reportId}")
    public ResponseEntity addComment(@PathVariable Integer reportId,
                                     @RequestBody FacilityReportDetail request) {
        return new ResponseEntity(housingDao.addComment(reportId, request), HttpStatus.OK);
    }

    // composite: find employeeId & name
    // Find comment of this employee. Update comment
    // return comment
    @PutMapping("comment/{commentId}")
    public ResponseEntity updateComment(@PathVariable Integer commentId,
                                        @RequestParam("comment") String comment) {
        return new ResponseEntity(housingDao.updateComment(commentId, comment), HttpStatus.OK);
    }

}

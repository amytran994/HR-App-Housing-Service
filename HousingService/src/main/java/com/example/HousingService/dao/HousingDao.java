package com.example.HousingService.dao;

import com.example.HousingService.domain.entity.*;
import com.example.HousingService.domain.request.AddFacilityRequest;
import com.example.HousingService.domain.request.AddHouseRequest;
import com.example.HousingService.domain.response.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HousingDao {
    protected SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /* For HR */

    // View all houses
    // return AllHouseResponse
    public AllHouseResponse getAllHouses() {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from House h join fetch h.landlord", House.class);

        List<House> houses = q.getResultList();
        List<HouseItem> houseItems = new ArrayList<>();

        for (House house:houses) {

            Landlord landlord = house.getLandlord();
            String fullname = landlord.getFirstName() + " " + landlord.getLastName();

            HouseItem item = HouseItem.builder()
            .address(house.getAddress())
            .landlordFullname(fullname)
            .email(landlord.getEmail())
            .phone(landlord.getCellPhone())
            .maxOccupant(house.getMaxOccupant())
            .build();

            houseItems.add(item);
        }

        return AllHouseResponse.builder().houses(houseItems).build();
    }

    // Add new house
    // return House
    public House addHouse(AddHouseRequest request) {
        Session session = sessionFactory.getCurrentSession();

        Landlord landlord = Landlord.builder()
        .firstName(request.getFirstname())
        .lastName(request.getLastname())
        .email(request.getEmail())
        .cellPhone(request.getPhone()).build();

        House house = House.builder()
        .address(request.getAddress())
        .landlord(landlord)
        .maxOccupant(request.getMaxOccupant()).build();

        session.save(landlord);
        session.save(house);

        return house;
    }

    // Add facility
    public int addFacility(Integer houseId, AddFacilityRequest request) {
        List<Facility> facilities = request.getFacilityList();
        Session session = sessionFactory.getCurrentSession();
        House house = session.get(House.class, houseId);

        int i = 0;
        for (Facility f : facilities) {
            f.setHouse(house);
            session.save(f);
            i++;
        }
        return i;
    }

    public Facility updateFacility(Facility newFacility, Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Facility facility = session.get(Facility.class, id);
        if (newFacility.getType() != null) {
            facility.setType(newFacility.getType());
        }
        if (newFacility.getDescription() != null) {
            facility.setDescription(newFacility.getDescription());
        }
        if (newFacility.getQuantity() != facility.getQuantity()) {
            facility.setQuantity(newFacility.getQuantity());
        }

        session.saveOrUpdate(facility);
        facility.setHouse(null);
        return facility;
    }

    // Delete a house
    // return message
    public int deleteHouse(Integer houseId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        int row = 0;
        try {
            Query q = session.createQuery("delete from Facility f where f.house.id = :houseId");
            row = q.setParameter("houseId", houseId).executeUpdate();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        return row;
    }

    private House getHouseByHouseId(Integer houseId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from House h where h.id = :houseId", House.class);
        List<House> houses = q.setParameter("houseId", houseId).getResultList();
        if (houses.size() == 0) {
            return null;
        }
        return houses.get(0);
    }

    private List<Facility> getFacilities(Integer houseId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from Facility f where f.houseId = :houseId", Facility.class);
        List<Facility> facilities = q.setParameter("houseId", houseId).getResultList();
        if (facilities.size() == 0) {
            return null;
        }
        return facilities;
    }

    private List<FacilityReport> getFacilityReport (Integer facilityId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from FacilityReport f where f.facilityId = :facilityId", FacilityReport.class);
        List<FacilityReport> reports = q.setParameter("facilityId", facilityId).getResultList();
        if (reports.size() == 0) {
            return null;
        }
        return reports;
    }

    private List<FacilityReportDetail> getFacilityReportDetail (Integer reportId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from FacilityReportDetail f where f.facilityReportId = :reportId", FacilityReportDetail.class);
        List<FacilityReportDetail> details = q.setParameter("facilityId", reportId).getResultList();
        if (details.size() == 0) {
            return null;
        }
        return details;
    }


    // composite
    // View House record (house info, landlord info, facility info, reports)
    // reports sorted in desc order of date
    // return HouseDetail
    public HouseDetail getHouse(Integer houseId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from House where id = :id", House.class);
        List<House> houses = q.setParameter("id", houseId).getResultList();
        if (houses.size() == 0) {

        }

        House house = houses.get(0);

        Landlord landlord = house.getLandlord();
        String fullname = landlord.getFirstName() + " " + landlord.getLastName();

        HouseDetail houseDetail = HouseDetail.builder()
        .address(house.getAddress())
        .landlordFullname(fullname)
        .build();

        Query q2 = session.createQuery("from Facility where houseId = :houseId", Facility.class);
        List<Facility> facilityList = q2.setParameter("houseId", houseId).getResultList();

        if (facilityList.size() == 0) {

        }
        List<Integer> idList = new ArrayList<>();

        for (int i = 0; i < facilityList.size(); ++i) {
            Facility facility = facilityList.get(i);
            idList.add(facility.getId());

            if (facility.getType().equalsIgnoreCase("Bed")) {
                houseDetail.setNumOfBed(facility.getQuantity());
            }
            else if (facility.getType().equalsIgnoreCase("Mattress")) {
                houseDetail.setNumOfMattress(facility.getQuantity());
            }
            else if (facility.getType().equalsIgnoreCase("Table")) {
                houseDetail.setNumOfTable(facility.getQuantity());
            }
            else if (facility.getType().equalsIgnoreCase("Chair")) {
                houseDetail.setNumOfChair(facility.getQuantity());
            }

        }

            Query q3 = session.
            createQuery("from FacilityReport f where facilityId in (:idList) order by f.reportedDate desc",
            FacilityReport.class);
            List<FacilityReport> facilityReports = q3.setParameter("idList", idList).getResultList();

            if (facilityReports.size() == 0) {

            }

            List<ReportItem> reports = new ArrayList<>();

            for (FacilityReport facilityReport : facilityReports) {

                ReportItem reportItem = ReportItem.builder()
                .reportedDate(facilityReport.getReportedDate())
                .status(facilityReport.getStatus())
                .tittle(facilityReport.getTitle())
                .build();

                reports.add(reportItem);
            }

            houseDetail.setReports(reports);


        return houseDetail;
    }


    /* For Employee */

    // composite: find houseId of this employee
    // view house
    public HouseDetail getEmployeeHouse(String houseId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from House where id = :id", House.class);
        List<House> houses = q.setParameter("id", houseId).getResultList();

        if (houses.size() == 0) {
        }

        House house = houses.get(0);
        HouseDetail houseDetail = HouseDetail.builder()
        .address(house.getAddress()).build();

        return houseDetail;
    }

    // composite
    // Add report
    public ReportDetail report(Integer facilityId, ReportDetail request) {
        Session session = sessionFactory.getCurrentSession();
        Facility facility = session.get(Facility.class, facilityId);
        if (request.getReportedDate() == null) {
            request.setReportedDate(LocalDateTime.now().toString());

        }

        FacilityReport report = FacilityReport.builder()
        .facility(facility)
        .title(request.getTitle())
        .status(request.getStatus())
        .description(request.getDescription())
        .employeeId(request.getEmployeeId())
        .reportedDate(request.getReportedDate()).build();

        Integer id = (Integer) session.save(report);
        request.setReportId(id);
        request.setEmployeeId(null);
        return request;
    }

    // Get all comments about this report
    public AllCommentResponse getAllComments(Integer reportId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery
        ("from FacilityReportDetail f where f.facilityReport.id = :reportId", FacilityReportDetail.class);

        List<FacilityReportDetail> list = q.setParameter("reportId", reportId).getResultList();
        if (list.size() == 0) {

        }

        List<Comment> comments = new ArrayList<>();

        for (FacilityReportDetail f : list) {
            String date = f.getCreateDate();
            if (f.getLastModificationDate() != null) {
                date = f.getLastModificationDate();
            }
            Comment comment = Comment.builder()
            .comment(f.getComment())
            .commentedDate(date)
            .employeeId(f.getEmployeeId())
            .build();

            comments.add(comment);
        }
        return AllCommentResponse.builder().comments(comments).build();
    }

    /* For All */

    // Add comment on report
    // Create new comment related to this report
    public FacilityReportDetail addComment(Integer facilityId, FacilityReportDetail request) {
        Session session = sessionFactory.getCurrentSession();
        FacilityReport report = session.get(FacilityReport.class, facilityId);

        request.setFacilityReport(report);
        if (request.getCreateDate() == null) {
            request.setCreateDate(LocalDateTime.now().toString());
        }
        session.saveOrUpdate(request);

        request.setFacilityReport(null);
        return request;
    }

    // Find comment of this employee. Update comment
    // return comment
    public FacilityReportDetail updateComment(Integer id, String comment) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        FacilityReportDetail f = session.get(FacilityReportDetail.class, id);

        f.setComment(comment);
        f.setLastModificationDate(LocalDateTime.now().toString());

        session.update(f);
        tx.commit();
        f.setFacilityReport(null);
        return f;
    }
}

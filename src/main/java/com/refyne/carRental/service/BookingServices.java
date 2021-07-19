package com.refyne.carRental.service;

import com.refyne.carRental.dao.Booking;
import com.refyne.carRental.dao.Cars;
import com.refyne.carRental.dao.User;
import com.refyne.carRental.dto.BookingDto;
import com.refyne.carRental.dto.BookingsByUser;
import com.refyne.carRental.dto.ResponseData;
import com.refyne.carRental.dto.SearchDto;
import com.refyne.carRental.exception.CarRentalException;
import com.refyne.carRental.mapper.BookingMapper;
import com.refyne.carRental.repo.BookingRepository;
import com.refyne.carRental.repo.CarsRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.StringBasedMongoQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServices {

    BookingRepository bookingRepository;
    MongoTemplate mongoTemplate;
    BookingMapper mapper;
    Queries queries;
    CarsRepository carsRepository;

    @Autowired
    public BookingServices(BookingRepository bookingRepository, MongoTemplate mongoTemplate, BookingMapper mapper, Queries queries, CarsRepository carsRepository) {
        this.bookingRepository = bookingRepository;
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
        this.queries = queries;
        this.carsRepository = carsRepository;
    }






    public ResponseData save(BookingDto dto) {

//        Query q = new Query(
//                new Criteria().andOperator(
//                    Criteria.where("carId").is(dto.getCarId()),
//                    new Criteria().orOperator(
//                        new Criteria().andOperator(
//                                Criteria.where("startTime").lte(start),
//                                Criteria.where("endTime").gte(end)
//                        ),
//                        new Criteria().andOperator(
//                                Criteria.where("startTime").gte(start),
//                                Criteria.where("endTime").gte(end)
//                        ),
//                        new Criteria().andOperator(
//                                Criteria.where("startTime").lte(start),
//                                Criteria.where("endTime").lte(end)
//                        ),
//                        new Criteria().andOperator(
//                                Criteria.where("startTime").gte(start),
//                                Criteria.where("endTime").lte(end)
//                        )
//                    )
//                )
//        );
       try {
        Optional<Cars> carFromDB = carsRepository.findById(dto.getCarId());
        if(!carFromDB.isPresent())
            return new ResponseData(false, "Car not present ", null, HttpStatus.CONFLICT.value());

           if(findOverLapping(dto.getStartTime(),  dto.getEndTime(), dto.getCarId()).isEmpty()) {
            return new ResponseData<Booking>(true, "New Booking entry created",
                    bookingRepository.save(mapper.get(dto, carFromDB.get())), HttpStatus.OK.value());
        }
        return new ResponseData(false, "Booking overlapping ", null, HttpStatus.CONFLICT.value());

        } catch (Exception e) {
            throw new CarRentalException("Error while adding new Car  data " + e.getMessage(), e.getCause());
        }
    }

    public List<Booking> findOverLapping(Date start, Date end, String carId) {
        try {

            Query q = new Query(
                    new Criteria().andOperator(
                            Criteria.where("carId").is(carId),
                            new Criteria().orOperator(
                                    new Criteria().andOperator(
                                            Criteria.where("startTime").gte(start),
                                            Criteria.where("startTime").lte(end)
                                    ),
                                    new Criteria().andOperator(
                                            Criteria.where("endTime").gte(start),
                                            Criteria.where("endTime").lte(end)
                                    ),
                                    new Criteria().andOperator(
                                            Criteria.where("startTime").lte(start),
                                            Criteria.where("endTime").gte(end)
                                    )
                            )
                    )
            );
            return mongoTemplate.find(q, Booking.class);
        }catch (Exception e) {
            throw new CarRentalException("Error while adding new Car  data " + e.getMessage(), e.getCause());
        }
    }




    public ResponseData<List<Cars>> getAllAvailableCars(SearchDto searchDto) {
        List<String> carIdsBooked = new ArrayList<>();
        if(Objects.nonNull(searchDto.getEndTime()) && Objects.nonNull(searchDto.getStartTime())) {
            List<Booking> overLappingBookings = new ArrayList<>();
            if(StringUtils.isNotBlank(searchDto.getVehicleId())) {
                 overLappingBookings = findOverLapping(searchDto.getStartTime(), searchDto.getEndTime(), searchDto.getVehicleId());
            } else {
                Query overlapping =  queries.timeOverLapQuery(searchDto.getStartTime(), searchDto.getEndTime());
                overLappingBookings = mongoTemplate.find(overlapping, Booking.class);
            }
            carIdsBooked = overLappingBookings.stream().map(x-> "\""+x.getCarId()+"\"").collect(Collectors.toList());

        }

        Query q = new Query();

        String nativeQuery = " { ";

        if(Objects.nonNull(searchDto.getVehicleId()))
           nativeQuery = nativeQuery.concat(" $and : [ { _id : \""+searchDto.getVehicleId()+"\" } , {_id: {$nin : "+carIdsBooked+" }}] ");
        if(Objects.nonNull(searchDto.getBasePriceMin()) && Objects.nonNull(searchDto.getBasePriceMin())) {
            if(nativeQuery.length() > 0)
                nativeQuery = nativeQuery.concat(" , ");
            nativeQuery = nativeQuery.concat("  basePrice : {$gte :"+searchDto.getBasePriceMin().toPlainString()+", $lte: "+searchDto.getBasePriceMax()+"}  " );
        }
        if(Objects.nonNull(searchDto.getPphMin()) && Objects.nonNull(searchDto.getPphMax())) {
            if(nativeQuery.length() > 0)
                nativeQuery = nativeQuery.concat(" , ");
            nativeQuery = nativeQuery.concat("  pph : {$gte :"+searchDto.getPphMin().toPlainString()+", $lte: "+searchDto.getPphMax()+"}  " );
        }
        if(Objects.nonNull(searchDto.getDepositMin()) && Objects.nonNull(searchDto.getDepositMax())) {
            if(nativeQuery.length() > 0)
                nativeQuery = nativeQuery.concat(" , ");
            nativeQuery = nativeQuery.concat("  deposit : {$gte :"+searchDto.getDepositMin().toPlainString()+", $lte: "+searchDto.getDepositMax()+"}  " );
        }
        if(StringUtils.isNotBlank(searchDto.getManufacturer())) {
            if(nativeQuery.length() > 0)
                nativeQuery = nativeQuery.concat(" , ");
            nativeQuery = nativeQuery.concat("  manufacturer : \""+searchDto.getManufacturer()+"\"" );
        }
        if(StringUtils.isNotBlank(searchDto.getModel())) {
            if(nativeQuery.length() > 0)
                nativeQuery = nativeQuery.concat(" , ");
            nativeQuery = nativeQuery.concat("  model : \""+searchDto.getModel()+"\"" );
        }

        nativeQuery = nativeQuery.concat( " } ");

        BasicQuery query = new BasicQuery(nativeQuery);
        List<Cars> cars = mongoTemplate.find(query, Cars.class);
        return new ResponseData<List<Cars>>(true, "",
                cars, HttpStatus.OK.value());
    }

    public List<BookingsByUser> getAllBookingForUser(User user) {
        return bookingRepository.findByUserId(user.getMobileNo());
    }

    public List<BookingsByUser> getAllBookingForCar(String vehicleId){
        return bookingRepository.findByCarIdAndStartTimeGreaterThan(vehicleId, Date.from(Instant.now()));
    }



}

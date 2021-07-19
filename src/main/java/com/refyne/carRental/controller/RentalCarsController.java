package com.refyne.carRental.controller;


import com.refyne.carRental.dao.Booking;
import com.refyne.carRental.dto.BookingDto;
import com.refyne.carRental.dto.CarDto;
import com.refyne.carRental.dto.ResponseData;
import com.refyne.carRental.dto.SearchDto;
import com.refyne.carRental.exception.CarRentalException;
import com.refyne.carRental.facade.BookingFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequestMapping("/v1/rent/")
@RestController
@Api(value = "Rental CONTROLLER")
public class RentalCarsController {

    BookingFacade bookingFacade;

    @Autowired
    public RentalCarsController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @ApiModelProperty(value = "Booking of a car ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request!")})
    @PostMapping(value = "")
    public ResponseEntity<ResponseData> save(@RequestBody BookingDto bookingDto) {
        try {
            ResponseData responseData = bookingFacade.createBooking(bookingDto);
            if (responseData.isSuccess()) {
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseData, HttpStatus.valueOf(responseData.getCode()));
            }
        } catch (CarRentalException e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiModelProperty(value = "Search Cars for rental")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 400, message = "Bad Request!")})
    @PostMapping(value = "search-cars")
    public ResponseEntity<ResponseData> search(
            @RequestBody(required = true) SearchDto searchDto) {

        try {
            return new ResponseEntity<>(bookingFacade.searchCars(searchDto), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}

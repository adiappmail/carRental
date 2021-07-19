package com.refyne.carRental.controller;


import com.refyne.carRental.dto.CarDto;
import com.refyne.carRental.dto.ResponseData;
import com.refyne.carRental.exception.CarRentalException;
import com.refyne.carRental.facade.CarsFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/v1/cars/")
@RestController
@Api(value = "CARS CONTROLLER")
public class CarsRestController {

    CarsFacade carsFacade;

    @Autowired
    public CarsRestController(CarsFacade carsFacade) {
        this.carsFacade = carsFacade;
    }

    @ApiModelProperty(value = "Add new Car Profile ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request!")})
    @PostMapping(value = "")
    public ResponseEntity<ResponseData> add(@RequestBody CarDto carDto) {
        try {
            ResponseData responseData = carsFacade.addNewCar(carDto);
            if (responseData.isSuccess()) {
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseData, HttpStatus.valueOf(responseData.getCode()));
            }
        } catch (CarRentalException e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiModelProperty(value = "Get Car Profile ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 400, message = "Bad Request!")})
    @GetMapping(value = "")
    public ResponseEntity<ResponseData> get(@RequestParam(required = false) String vehicleId) {

        try {
            return new ResponseEntity<>(carsFacade.getVehicle(vehicleId), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @ApiModelProperty(value = "Del Car Profile ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 400, message = "Bad Request!")})
    @DeleteMapping(value = "")
    public ResponseEntity<ResponseData> delete(
            @RequestParam(required = false) String id) {

        try {
            return new ResponseEntity<>(carsFacade.deleteCar(id), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiModelProperty(value = "Edit User Profile ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 400, message = "Bad Request!")})
    @PutMapping(value = "")
    public ResponseEntity<ResponseData> edit(
            @RequestBody(required = false) CarDto userDto) {

        try {
            return new ResponseEntity<>(carsFacade.updateCarData(userDto), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @ApiModelProperty(value = "Get Car Cars ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 400, message = "Bad Request!")})
    @GetMapping(value = "search")
    public ResponseEntity<ResponseData> getProfile(@RequestParam(required = false) String vehicleId,
                           @RequestParam(required = false) String model,
                           @RequestParam(required = false) String manufacturer) {

        try {
            return new ResponseEntity<>(carsFacade.search(vehicleId, model, manufacturer), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiModelProperty(value = "Get Car Cars ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 400, message = "Bad Request!")})
    @GetMapping(value = "calculate-price")
    public ResponseEntity<ResponseData> calculatePrice(@RequestParam(required = true) String vehicleId,
                                                       @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDateTime,
                                                       @RequestParam(required = true)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date  toDateTime) {

        try {
            return new ResponseEntity<>(carsFacade.getTotalCostForTime(vehicleId, fromDateTime,toDateTime), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiModelProperty(value = "Get Booking against Cars ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 400, message = "Bad Request!")})
    @GetMapping(value = "bookings")
    public ResponseEntity<ResponseData> getFutureBookings(@RequestParam(required = true) String vehicleId) {
        try {
            return new ResponseEntity(carsFacade.getAllBookingsForCar(vehicleId), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

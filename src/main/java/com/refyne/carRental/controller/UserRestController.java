package com.refyne.carRental.controller;


import com.refyne.carRental.dto.ResponseData;
import com.refyne.carRental.dto.UserDto;
import com.refyne.carRental.exception.CarRentalException;
import com.refyne.carRental.facade.UserFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequestMapping("/v1/user/")
@RestController
@Api(value = "USERS CONTROLLER")
public class UserRestController {

    UserFacade userFacade;

    @Autowired
    public UserRestController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @ApiModelProperty(value = "Add new User Profile ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request!")})
    @PostMapping(value = "")
    public ResponseEntity<ResponseData> add(@RequestBody UserDto userDto) {
        try {
            ResponseData responseData = userFacade.addNewUser(userDto);
            if (responseData.isSuccess()) {
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseData, HttpStatus.valueOf(responseData.getCode()));
            }
        } catch (CarRentalException e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiModelProperty(value = "Get User Profile ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 400, message = "Bad Request!")})
    @GetMapping(value = "")
    public ResponseEntity<ResponseData> get(@RequestParam(required = false) String mobileNo) {

        try {
            return new ResponseEntity<>(userFacade.getUser(mobileNo), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @ApiModelProperty(value = "Del User Profile ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 400, message = "Bad Request!")})
    @DeleteMapping(value = "")
    public ResponseEntity<ResponseData> delete(
            @RequestParam(required = false) String id) {

        try {
            return new ResponseEntity<>(userFacade.deleteUser(id), HttpStatus.OK);

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
            @RequestBody(required = false) UserDto userDto) {

        try {
            return new ResponseEntity<>(userFacade.updateUserProfile(userDto), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @ApiModelProperty(value = "Get All booking for a user ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 400, message = "Bad Request!")})
    @GetMapping(value = "bookings")
    public ResponseEntity<ResponseData> getBookings(@RequestParam(required = true) String mobileNumber) {
        try {
            return new ResponseEntity<>(userFacade.getAllBookingByUser(mobileNumber), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}

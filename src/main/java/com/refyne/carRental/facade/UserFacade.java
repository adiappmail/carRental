package com.refyne.carRental.facade;

import com.refyne.carRental.dao.Booking;
import com.refyne.carRental.dao.User;
import com.refyne.carRental.dto.BookingsByUser;
import com.refyne.carRental.dto.ResponseData;
import com.refyne.carRental.dto.UserDto;
import com.refyne.carRental.exception.CarRentalException;
import com.refyne.carRental.service.BookingServices;
import com.refyne.carRental.service.UserServices;
import com.refyne.carRental.utils.UserUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserFacade {

    UserServices userServices;
    BookingServices bookingServices;

    @Autowired
    public UserFacade(UserServices userServices, BookingServices bookingServices) {
        this.userServices = userServices;
        this.bookingServices = bookingServices;
    }

    public ResponseData updateUserProfile(UserDto userDto) throws CarRentalException {

        String validations = UserUtility.validateUpdate(userDto);
        if(StringUtils.isNotBlank(validations)) {
            return new ResponseData(false, validations, null, HttpStatus.BAD_REQUEST.value());
        }
        return userServices.update(userDto);
    }

    public ResponseData addNewUser(UserDto userDto) throws CarRentalException {

        String validations = UserUtility.validateUser(userDto);
        if(StringUtils.isNotBlank(validations)) {
            return new ResponseData(false, validations, null, HttpStatus.BAD_REQUEST.value());
        }
        return userServices.save(userDto);
    }

    public ResponseData deleteUser(String mobileNo) throws CarRentalException {

        if(StringUtils.isBlank(mobileNo) || mobileNo.length() != 10) {
            return new ResponseData(false, "Mobile number cannot be null or should be atleast 10 digit", null, HttpStatus.BAD_REQUEST.value());
        }
        return userServices.delete(mobileNo);
    }


    public ResponseData<User>  getUser(String mobileNo) {
        if(Objects.isNull(mobileNo) || mobileNo.length() != 10) {
            return new ResponseData(false, "Mobile number cannot be null or should be atleast 10 digit", null, HttpStatus.BAD_REQUEST.value());
        }

        try {
            Optional<User> userFromDB = userServices.get(mobileNo);
            if (!userFromDB.isPresent()) {
                return new ResponseData(false, "User Profile not  present", null, HttpStatus.NOT_FOUND.value());
            }
            return new ResponseData<User>(true, " ",
                    userFromDB.get(), HttpStatus.OK.value());
        }catch (Exception e) {
            throw new CarRentalException("Error while fetching data " + e.getMessage(), e.getCause());
        }

    }

    public ResponseData<List<Booking>> getAllBookingByUser(String mobileNo) {
        if(Objects.isNull(mobileNo) || mobileNo.length() != 10) {
            return new ResponseData(false, "Mobile number cannot be null or should be atleast 10 digit", null, HttpStatus.BAD_REQUEST.value());
        }
        Optional<User> userFromDB = userServices.get(mobileNo);
        if (!userFromDB.isPresent()) {
            return new ResponseData(false, "User Profile not  present", null, HttpStatus.NOT_FOUND.value());
        }
        return new ResponseData(true, "",
                bookingServices.getAllBookingForUser(userFromDB.get()),
                HttpStatus.OK.value());

    }
}

package com.refyne.carRental.service;


import com.refyne.carRental.dao.Booking;
import com.refyne.carRental.dao.User;
import com.refyne.carRental.dto.ResponseData;
import com.refyne.carRental.dto.UserDto;
import com.refyne.carRental.exception.CarRentalException;
import com.refyne.carRental.mapper.UserMapper;
import com.refyne.carRental.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServices {

    UserRepository userRepository;
    UserMapper mapper;


    @Autowired
    public UserServices(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public ResponseData<User> save(UserDto newUserDto) {

        try {
            Optional<User> userFromDB = userRepository.findById(newUserDto.getMobileNo());

            if (userFromDB.isPresent()) {
                return new ResponseData(false, "USer Profile with same mobile is already present", null, HttpStatus.CONFLICT.value());
            }

            User newUser = userRepository.save(mapper.get(newUserDto));
            return new ResponseData<User>(true, "New Profile created",
                    newUser, HttpStatus.OK.value());

        } catch (Exception e) {
            throw new CarRentalException("Error while adding profile  data " + e.getMessage(), e.getCause());
        }
    }


    public ResponseData<User> update(UserDto userDto) {

        try {
            Optional<User> userFromDB = userRepository.findById(userDto.getMobileNo());

            if (!userFromDB.isPresent()) {
                return new ResponseData(false, "User Profile not  present", null, HttpStatus.NOT_FOUND.value());
            }

            User updatedDao = mapper.get(userDto, userFromDB.get());
            if (Objects.isNull(updatedDao)) {
                return new ResponseData(false, "Same data cannot update", null, HttpStatus.BAD_REQUEST.value());
            }


            return new ResponseData<User>(true, "Updated Profile",
                    userRepository.save(updatedDao)
                    , HttpStatus.OK.value());

        } catch (Exception e) {
            throw new CarRentalException("Error while updating data " + e.getMessage(), e.getCause());
        }

    }


    public ResponseData<User> delete(String mobileNo) {
        try {
            Optional<User> userFromDB = userRepository.findById(mobileNo);

            if (!userFromDB.isPresent()) {
                return new ResponseData(false, "User Profile not  present", null, HttpStatus.NOT_FOUND.value());
            }
            userRepository.delete(userFromDB.get());
            return new ResponseData<User>(true, " Profile deleted successfully of Mobile No".concat(String.valueOf(mobileNo)),
                    null, HttpStatus.OK.value());

        } catch (Exception e) {
            throw new CarRentalException("Error while deleting data " + e.getMessage(), e.getCause());
        }

    }

    public Optional<User> get(String mobileNo) {

        return userRepository.findById(mobileNo);

    }

}

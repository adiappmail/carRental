package com.refyne.carRental.facade;

import com.refyne.carRental.dao.Booking;
import com.refyne.carRental.dao.Cars;
import com.refyne.carRental.dao.User;
import com.refyne.carRental.dto.CarDto;
import com.refyne.carRental.dto.ResponseData;
import com.refyne.carRental.exception.CarRentalException;
import com.refyne.carRental.service.BookingServices;
import com.refyne.carRental.service.CarsServices;
import com.refyne.carRental.utils.CarUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class CarsFacade {

    CarsServices carsServices;
    BookingServices bookingServices;

    @Autowired
    public CarsFacade(CarsServices carsServices, BookingServices bookingServices) {
        this.carsServices = carsServices;
        this.bookingServices = bookingServices;
    }

    public ResponseData updateCarData(CarDto carDto) throws CarRentalException {

        String validations = CarUtility.validateUpdate(carDto);
        if (StringUtils.isNotBlank(validations)) {
            return new ResponseData(false, validations, null, HttpStatus.BAD_REQUEST.value());
        }
        return carsServices.update(carDto);
    }

    public ResponseData addNewCar(CarDto carDto) throws CarRentalException {

        String validations = CarUtility.validate(carDto);
        if (StringUtils.isNotBlank(validations)) {
            return new ResponseData(false, validations, null, HttpStatus.BAD_REQUEST.value());
        }
        return carsServices.save(carDto);
    }

    public ResponseData deleteCar(String vehicleID) throws CarRentalException {

        if (StringUtils.isBlank(vehicleID)) {
            return new ResponseData(false, "Vehicle number cannot be null", null, HttpStatus.BAD_REQUEST.value());
        }
        return carsServices.delete(vehicleID);
    }


    public ResponseData<Cars> getVehicle(String vehicle) {
        if (Objects.isNull(vehicle) ) {
            return new ResponseData(false, "Vehicle number cannot be null ", null, HttpStatus.BAD_REQUEST.value());
        }
        try {
            Optional<Cars> carFromDB = carsServices.get(vehicle);
            if (!carFromDB.isPresent()) {
                return new ResponseData(false, "Cars Profile not  present", null, HttpStatus.NOT_FOUND.value());
            }
            return new ResponseData<Cars>(true, " ",
                    carFromDB.get(), HttpStatus.OK.value());
        }catch (Exception e) {
            throw new CarRentalException("Error while fetching data " + e.getMessage(), e.getCause());
        }
    }

    public ResponseData<List<Cars>> search(String id, String model, String manufacturer) {

        return carsServices.searchDB(id,  model,  manufacturer) ;

    }

    public ResponseData<BigDecimal> getTotalCostForTime(String vehicleId, Date startTime, Date endTime) {

        String validations = CarUtility.validate(startTime, endTime);
        if (StringUtils.isNotBlank(validations)) {
            return new ResponseData(false, validations, null, HttpStatus.BAD_REQUEST.value());
        }

        return carsServices.calculateCost(vehicleId, startTime, endTime);
    }

    public ResponseData<List<Booking>> getAllBookingsForCar(String vehicleId) {

        Optional<Cars> carFromDB=  carsServices.get(vehicleId);
        if(!carFromDB.isPresent()) {
            return new ResponseData(false, "Cars Profile not  present", null, HttpStatus.NOT_FOUND.value());
        }
        return new ResponseData(true, "", bookingServices.getAllBookingForCar(carFromDB.get().getVehicleId()),
                HttpStatus.OK.value());
    }
}

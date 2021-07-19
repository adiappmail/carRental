package com.refyne.carRental.facade;

import com.refyne.carRental.dao.Booking;
import com.refyne.carRental.dao.Cars;
import com.refyne.carRental.dto.BookingDto;
import com.refyne.carRental.dto.ResponseData;
import com.refyne.carRental.dto.SearchDto;
import com.refyne.carRental.service.BookingServices;
import com.refyne.carRental.service.CarsServices;
import com.refyne.carRental.service.Queries;
import com.refyne.carRental.utils.BookingUtility;
import com.refyne.carRental.utils.CarUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BookingFacade {

    BookingServices bookingServices;
    CarsServices carsServices;
    Queries queries;

    @Autowired
    public BookingFacade(BookingServices bookingServices, CarsServices carsServices, Queries queries) {
        this.bookingServices = bookingServices;
        this.carsServices = carsServices;
        this.queries = queries;
    }

    public ResponseData createBooking(BookingDto bookingDto) {
        String validations = BookingUtility.validate(bookingDto);
        if (StringUtils.isNotBlank(validations)) {
            return new ResponseData(false, validations, null, HttpStatus.BAD_REQUEST.value());
        }
        return bookingServices.save(bookingDto);
    }

    public ResponseData searchCars(SearchDto searchDto) {

        if(Objects.isNull(searchDto))
            return carsServices.getAllCars();


        return bookingServices.getAllAvailableCars(searchDto);
    }
}

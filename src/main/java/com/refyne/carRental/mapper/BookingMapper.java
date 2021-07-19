package com.refyne.carRental.mapper;

import com.refyne.carRental.dao.Booking;
import com.refyne.carRental.dao.Cars;
import com.refyne.carRental.dto.BookingDto;
import com.refyne.carRental.utils.TimeUtility;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class BookingMapper {

    public Booking get(BookingDto dto, Cars car){

        Booking b = new Booking();
        b.setBookingId(UUID.randomUUID().toString());
        b.setCarId(dto.getCarId());
        b.setEndTime(dto.getEndTime());
        b.setStartTime(dto.getStartTime());
        b.setUserId(dto.getUserId());
        b.setValid(true);
        b.setHours(Integer.valueOf(TimeUtility.diffInHoursCeil(b.getStartTime() , b.getEndTime())));
        b.setTotalCost(car.getPph().multiply(new BigDecimal(b.getHours())));
        return b;
    }
}

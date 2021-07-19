package com.refyne.carRental.utils;

import com.refyne.carRental.dao.Booking;
import com.refyne.carRental.dto.BookingDto;
import lombok.experimental.UtilityClass;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Objects;

@UtilityClass
public class BookingUtility {


    public String validate(BookingDto dto) {
        String resonse = "";
        if(Objects.isNull(dto.getCarId()))
            resonse = resonse.concat(" CarID cannot be null");
        if(Objects.isNull(dto.getUserId()))
            resonse = resonse.concat(" User ID cannot be null");
        if(Objects.isNull(dto.getStartTime()))
            resonse = resonse.concat("Start  cannot be null");
    //        else {
    //            if(dto.getStartTime().before(Date.from(new Date().toInstant())))
    //                resonse = resonse.concat(" Start date cannot be before current time");
    //        }
        if(Objects.isNull(dto.getEndTime()))
            resonse = resonse.concat(" End time cannot be null");
        else {
            if(dto.getStartTime().before(Date.from(new Date().toInstant())))
                resonse = resonse.concat(" End date cannot be before current time");
        }
        if(Objects.nonNull(dto.getStartTime()) &&
                Objects.nonNull(dto.getEndTime()) &&
                 dto.getStartTime().after(dto.getEndTime()))
                resonse = resonse.concat(" Start date > End date");

        return resonse;
    }

}

package com.refyne.carRental.utils;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class TimeUtility {

    public Integer diffInHoursCeil(Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        long diffHours = diff / (60 * 60 * 1000) ;
        return (int) Math.ceil(Double.valueOf(diffHours));
    }
}

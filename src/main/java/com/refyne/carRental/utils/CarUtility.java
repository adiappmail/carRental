package com.refyne.carRental.utils;

import com.refyne.carRental.dto.CarDto;
import com.refyne.carRental.dto.UserDto;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Objects;

@UtilityClass
public class CarUtility {


    public String validate(CarDto dto) {

        String response = "";
        if(StringUtils.isBlank(dto.getVehicleId())) {
            response = response.concat(" Vehicle Id cannot be blank");
        }
        if(StringUtils.isBlank(dto.getModel())) {
            response = response.concat(" Model cannot be blank");
        }
        if(StringUtils.isBlank(dto.getManufacturer())) {
            response = response.concat(" Manufacturer cannot be blank");
        }
        if(Objects.isNull(dto.getDeposit())) {
        response = response.concat("Deposit  cannot be blank");
        }
        if(Objects.isNull(dto.getPph())){
            response = response.concat("Per hour cost  cannot be blank");
        }
        if(Objects.isNull(dto.getBasePrice())) {
        response = response.concat("Base price  cannot be blank");
        }


        return response;
    }
    public String validateUpdate(CarDto dto) {

        if(Objects.isNull(dto)) {
            return  "* Update object is required";
        }
        if(StringUtils.isBlank(dto.getVehicleId())) {
            return  " Cannot udpate without vehicle ID";
        }
        return "";
    }

    public String validate(Date startTime, Date endTime){

        if(startTime.after(endTime))
            return "end time cannot be before start time";
//        if(startTime.before(Date.from(new Date().toInstant())))
//            return "start time cannot be before current time";
        return "";

    }
}

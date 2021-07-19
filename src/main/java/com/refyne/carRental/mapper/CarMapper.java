package com.refyne.carRental.mapper;

import com.refyne.carRental.dao.Cars;
import com.refyne.carRental.dto.CarDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class CarMapper {


    public Cars get(CarDto carDto) {

        Cars cars= new Cars();
        cars.setBasePrice(carDto.getBasePrice());
        cars.setDeposit(carDto.getDeposit());
        cars.setManufacturer(carDto.getManufacturer());
        cars.setModel(carDto.getModel());
        cars.setPph(carDto.getPph());
        cars.setVehicleId(carDto.getVehicleId());
        return cars;
    }

    public  Cars get(CarDto dto, Cars cars) {

        boolean diff = false;
        if (StringUtils.isNotBlank(dto.getManufacturer()) &&
                !cars.getManufacturer().equalsIgnoreCase(dto.getManufacturer())) {
            cars.setManufacturer(dto.getManufacturer());
            diff = true;
        }
        if (StringUtils.isNotBlank(dto.getModel()) &&
                !cars.getModel().equalsIgnoreCase(dto.getModel())) {
            cars.setModel(dto.getModel());
            diff = true;
        }

        if (Objects.nonNull(dto.getBasePrice()) &&
                !cars.getBasePrice().equals(dto.getBasePrice())
                    && dto.getBasePrice().compareTo(BigDecimal.ZERO) > 0) {
            cars.setBasePrice(dto.getBasePrice());
            diff = true;
        }

        if (Objects.nonNull(dto.getPph()) &&
                !cars.getPph().equals(dto.getPph())
                && dto.getPph().compareTo(BigDecimal.ZERO) > 0) {
            cars.setPph(dto.getPph());
            diff = true;
        }

        if (Objects.nonNull(dto.getDeposit()) &&
                !cars.getDeposit().equals(dto.getDeposit())
                && dto.getDeposit().compareTo(BigDecimal.ZERO) > 0) {
            cars.setDeposit(dto.getDeposit());
            diff = true;
        }


        if(!diff) {
            return null;
        }
        return cars;
    }

}

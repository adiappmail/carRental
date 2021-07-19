package com.refyne.carRental.service;

import com.refyne.carRental.dao.Cars;
import com.refyne.carRental.dao.User;
import com.refyne.carRental.dto.CarDto;
import com.refyne.carRental.dto.ResponseData;
import com.refyne.carRental.dto.UserDto;
import com.refyne.carRental.exception.CarRentalException;
import com.refyne.carRental.mapper.CarMapper;
import com.refyne.carRental.repo.CarsRepository;
import com.refyne.carRental.utils.TimeUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarsServices {

    CarsRepository carsRepository;
    CarMapper mapper;

    @Autowired
    public CarsServices(CarsRepository carsRepository, CarMapper mapper) {
        this.carsRepository = carsRepository;
        this.mapper = mapper;
    }

    public ResponseData<Cars> save(CarDto newCarDto) {

        try {
            Optional<Cars> carFromDB = carsRepository.findById(newCarDto.getVehicleId());

            if (carFromDB.isPresent()) {
                return new ResponseData(false, "Car with same vehicle Id is already present", null, HttpStatus.CONFLICT.value());
            }

            Cars newCar = carsRepository.save(mapper.get(newCarDto));
            return new ResponseData<Cars>(true, "New Car entry created",
                    newCar, HttpStatus.OK.value());

        } catch (Exception e) {
            throw new CarRentalException("Error while adding new Car  data " + e.getMessage(), e.getCause());
        }
    }


    public ResponseData<Cars> update(CarDto userDto) {

        try {
            Optional<Cars> carFromDB = carsRepository.findById(userDto.getVehicleId());

            if (!carFromDB.isPresent()) {
                return new ResponseData(false, "Car not  present", null, HttpStatus.NOT_FOUND.value());
            }

            Cars updatedDao = mapper.get(userDto, carFromDB.get());
            if (Objects.isNull(updatedDao)) {
                return new ResponseData(false, "Same data cannot update", null, HttpStatus.BAD_REQUEST.value());
            }


            return new ResponseData<Cars>(true, "Updated Car data",
                    carsRepository.save(updatedDao)
                    , HttpStatus.OK.value());

        } catch (Exception e) {
            throw new CarRentalException("Error while updating data " + e.getMessage(), e.getCause());
        }

    }


    public ResponseData<Cars> delete(String vehicleId) {
        try {
            Optional<Cars> carFromDB = carsRepository.findById(vehicleId);

            if (!carFromDB.isPresent()) {
                return new ResponseData(false, "Car not  present", null, HttpStatus.NOT_FOUND.value());
            }
            // Check in booking data
            carsRepository.delete(carFromDB.get());
            return new ResponseData<Cars>(true, " Car deleted successfully of Mobile No".concat(String.valueOf(vehicleId)),
                    null, HttpStatus.OK.value());

        } catch (Exception e) {
            throw new CarRentalException("Error while deleting data " + e.getMessage(), e.getCause());
        }
    }

    public Optional<Cars> get(String vehicleNo) {
       return carsRepository.findById(vehicleNo);
    }

    public ResponseData<List<Cars>> searchDB(String vehicleNo,  String model, String manufacturer) {
        try {

            Cars c =new Cars();
            if(StringUtils.isNotBlank(vehicleNo))
                c.setVehicleId(vehicleNo);
            if(StringUtils.isNotBlank(model))
                c.setModel(model);
            if(StringUtils.isNotBlank(manufacturer))
                c.setManufacturer(manufacturer);
            Example<Cars> example = Example.of(c);
            List<Cars> cars =  carsRepository.findAll(example);

            if (cars.isEmpty()) {
                return new ResponseData(false, "Cars  not  present matching to query", null, HttpStatus.NOT_FOUND.value());
            }
            return new ResponseData<List<Cars>>(true, " ",
                    cars, HttpStatus.OK.value());
        }catch (Exception e) {
            throw new CarRentalException("Error while fetching data " + e.getMessage(), e.getCause());
        }

    }

    public ResponseData<List<Cars>> getAllCars() {
      return new ResponseData<List<Cars>>(true, " ", carsRepository.findAll(), HttpStatus.OK.value());
    }

    public ResponseData<BigDecimal> calculateCost(String vehicleId, Date startTime, Date endTime) {

        Optional<Cars> carFromDB = carsRepository.findById(vehicleId);

        if(carFromDB.isPresent()) {

            return new ResponseData<>(true, "",
                    carFromDB.get().getPph().multiply(new BigDecimal(TimeUtility.diffInHoursCeil(startTime, endTime)) ),
                    HttpStatus.OK.value());
        }
        return new ResponseData(false, "Cars  not  present matching to query", null, HttpStatus.NOT_FOUND.value());
    }
}

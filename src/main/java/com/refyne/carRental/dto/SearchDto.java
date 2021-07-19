package com.refyne.carRental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {

    String vehicleId;
    String manufacturer;
    String model;
    BigDecimal basePriceMin;
    BigDecimal basePriceMax;
    BigDecimal pphMin;
    BigDecimal pphMax;
    BigDecimal depositMin;
    BigDecimal depositMax;
    Date startTime;
    Date endTime;
}

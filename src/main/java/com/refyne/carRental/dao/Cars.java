package com.refyne.carRental.dao;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@Document("cars")
public class Cars {


    @Id
    String vehicleId;

    String manufacturer;

    String model;
    @Field(targetType = FieldType.DECIMAL128)
    BigDecimal basePrice;
    @Field(targetType = FieldType.DECIMAL128)
    BigDecimal pph;
    @Field(targetType = FieldType.DECIMAL128)
    BigDecimal deposit;


}




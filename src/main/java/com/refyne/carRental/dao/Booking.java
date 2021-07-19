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
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@Document("bookings")
public class Booking {

    @Id
    String bookingId;
    String carId;
    String userId;
    boolean valid;
    Date startTime;
    Date endTime;
    Integer hours;
    @Field(targetType = FieldType.DECIMAL128)
    BigDecimal totalCost;
}

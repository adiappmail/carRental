package com.refyne.carRental.dao;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@Document("user")
public class User {
    String userId;
    String name;
    @Id
    String mobileNo;
}

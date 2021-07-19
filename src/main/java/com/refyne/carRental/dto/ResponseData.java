package com.refyne.carRental.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> implements Serializable {
    private boolean success;
    private String errorMessage;
    private T result;
    private Integer code;

    public ResponseData(T result) {
        this.result = result;
        this.success = true;
    }

}
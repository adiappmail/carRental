package com.refyne.carRental.utils;

import com.refyne.carRental.dto.UserDto;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@UtilityClass
public class UserUtility {

    public String validateUser(UserDto dto) {

        String response = "";
        if(StringUtils.isBlank(dto.getName())) {
            response = response.concat(" Name cannot be blank");
        }
        if(StringUtils.isBlank(dto.getMobileNo())) {
            response = response.concat(" Mobile cannot be blank");
        } else if (dto.getMobileNo().trim().length() != 10) {
            response = response.concat(" Mobile number should be 10 digits");
        }

        return response;
    }

    public String validateUpdate(UserDto dto) {

        if(Objects.isNull(dto)) {
            return  "* Update object is required";
        }
        if(StringUtils.isBlank(dto.getMobileNo())) {
            return  " Cannot udpate without mobile";
        }
        return "";
    }


}

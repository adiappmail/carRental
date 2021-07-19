package com.refyne.carRental.mapper;

import com.refyne.carRental.dao.User;
import com.refyne.carRental.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {

    public User get(UserDto dto){

        User user = new User();
        user.setMobileNo(dto.getMobileNo());
        user.setName(dto.getName());
        user.setUserId(UUID.randomUUID().toString());
        return user;
    }

    public User get(UserDto dto, User user) {

        boolean diff = false;

        if (StringUtils.isNotBlank(dto.getName()) &&
                !user.getName().equalsIgnoreCase(dto.getName())) {
            user.setName(dto.getName());
            diff = true;
        }
        if(!diff) {
            return null;
        }
        return user;
    }

}

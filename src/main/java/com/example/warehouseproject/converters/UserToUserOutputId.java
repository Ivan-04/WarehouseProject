package com.example.warehouseproject.converters;

import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.UserOutputId;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserToUserOutputId implements Converter<User, UserOutputId> {

    @Override
    public UserOutputId convert(User user) {
        return UserOutputId.builder()
                .userId(String.valueOf(user.getUserId()))
                .build();
    }
}

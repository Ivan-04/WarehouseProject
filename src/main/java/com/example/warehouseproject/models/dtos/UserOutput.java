package com.example.warehouseproject.models.dtos;

import com.example.warehouseproject.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOutput {

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

}

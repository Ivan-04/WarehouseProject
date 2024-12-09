package com.example.warehouseproject.services.contracts;

import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.Register;
import com.example.warehouseproject.models.dtos.UserOutput;
import com.example.warehouseproject.models.dtos.UserOutputId;

import java.util.List;

public interface UserService {
    boolean existsByEmail(String email);

    List<UserOutput> findAll();

    UserOutput findUserById(int id);

    User findUserEntityById(int id);

    UserOutput findUserByFirstName(String firstName);

    UserOutput findUserByLastName(String lastName);

    UserOutput findUserByEmail(String email);

    User findUserEntityByEmail(String email);

    UserOutputId createUser(Register register);
}

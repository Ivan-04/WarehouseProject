package com.example.warehouseproject;

import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.Role;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.Warehouse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class HelperClass {


    public static ObjectMapper objectMapper = new ObjectMapper();


    public static User createMockUserUser(){
        var mockUser = new User();

        mockUser.setUserId(1);
        mockUser.setFirstName("Peter");
        mockUser.setLastName("Jackson");
        mockUser.setEmail("ppp.jjj@example.com");
        mockUser.setPassword("15975248653");
        mockUser.setRole(Role.OPERATOR);

        return mockUser;
    }

    public static User createMockUserOwner(){
        var mockUser = new User();

        mockUser.setUserId(2);
        mockUser.setFirstName("Gogo");
        mockUser.setLastName("Tomov");
        mockUser.setEmail("ggg.ttt@example.com");
        mockUser.setPassword("15975253");
        mockUser.setRole(Role.OWNER);

        return mockUser;
    }

    public static Warehouse createMockWarehouse(){
        var mockWarehouse = new Warehouse();

        mockWarehouse.setWarehouseId(1);
        mockWarehouse.setName("Warehouse");

        return mockWarehouse;
    }

    public static Part createMockPart(){
        var mockPart = new Part();

        mockPart.setTitle("Hammer");
        mockPart.setPartId(1);
        mockPart.setPrice(10.50);
        mockPart.setCreatedAt(LocalDateTime.now());
        mockPart.setDescription("Hammer is hammer");

        return mockPart;
    }
}

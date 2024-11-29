package com.example.warehouseproject.models.dtos;

import com.example.warehouseproject.models.Action;
import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.Warehouse;
import lombok.Builder;
import lombok.Data;

import java.awt.desktop.ScreenSleepEvent;
import java.time.LocalDateTime;

@Builder
@Data
public class WarehouseLogInput {

    private User user;

    private Warehouse warehouse;

    private Part part;

    private Action action;

    private int quantity;

}

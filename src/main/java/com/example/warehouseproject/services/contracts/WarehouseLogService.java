package com.example.warehouseproject.services.contracts;

import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.WarehouseLogInput;
import com.example.warehouseproject.models.dtos.WarehouseLogOutput;

import java.util.List;

public interface WarehouseLogService {
    List<WarehouseLogOutput> findAllWarehouseLogs(User user);

    WarehouseLogOutput findWarehouseLogById(int id, User user);

    void createWarehouseLog(WarehouseLogInput warehouseLogInput);
}

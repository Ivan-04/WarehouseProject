package com.example.warehouseproject.services.contracts;

import com.example.warehouseproject.models.dtos.WarehouseLogInput;
import com.example.warehouseproject.models.dtos.WarehouseLogOutput;

import java.util.List;

public interface WarehouseLogService {
    List<WarehouseLogOutput> findAllWarehouseLogs();

    WarehouseLogOutput findWarehouseLogById(int id);

    void createWarehouseLog(WarehouseLogInput warehouseLogInput);
}

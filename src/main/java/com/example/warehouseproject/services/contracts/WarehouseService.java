package com.example.warehouseproject.services.contracts;

import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.Warehouse;
import com.example.warehouseproject.models.dtos.WarehouseInput;
import com.example.warehouseproject.models.dtos.WarehouseOutput;
import com.example.warehouseproject.models.dtos.WarehouseOutputId;

import java.util.List;

public interface WarehouseService {
    List<WarehouseOutput> findAllWarehouses();

    WarehouseOutput findWarehouseById(int id);

    Warehouse findWarehouseEntityById(int id);

    WarehouseOutput findWarehouseByName(String title);

    WarehouseOutputId createWarehouse(WarehouseInput warehouseInput);

    void addPartToWarehouse(User user, String warehouseName, String partTitle, int quantityOfPart);
}

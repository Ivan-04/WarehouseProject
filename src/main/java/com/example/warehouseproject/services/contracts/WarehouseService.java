package com.example.warehouseproject.services.contracts;

import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.Warehouse;
import com.example.warehouseproject.models.dtos.WarehouseInput;
import com.example.warehouseproject.models.dtos.WarehouseOutput;
import com.example.warehouseproject.models.dtos.WarehouseOutputId;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface WarehouseService {
    Page<Warehouse> getWarehousesWithFilters(String name, int page, int size, String sortBy, String sortDirection);

    Map<Part, Integer> getAllPartsOfWarehouse(int id);

    List<WarehouseOutput> findAllWarehouses();

    List<Warehouse> findAllWarehousesEntities();

    WarehouseOutput findWarehouseById(int id);

    Warehouse findWarehouseEntityById(int id);

    WarehouseOutput findWarehouseByName(String title);

    Warehouse findWarehouseEntityByName(String title);

    WarehouseOutputId createWarehouse(WarehouseInput warehouseInput);

    void changeNameOfWarehouse(int id, String name, String email);

    void addPartToWarehouse(User user, String warehouseName, String partTitle, int quantityOfPart);

    void removePartFromWarehouse(User user, String warehouseName, String partTitle, int quantityOfPart);
}

package com.example.warehouseproject.services.contracts;

import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.Warehouse;
import com.example.warehouseproject.models.WarehousePart;
import com.example.warehouseproject.models.dtos.WarehousePartOutput;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WarehousePartService {


    List<WarehousePartOutput> findAllWarehousesParts();

    List<WarehousePart> getWarehousePartsWithFilters(String title);

    WarehousePart findWarehousePart(Warehouse warehouse, Part part);

    void createWarehousePart(Warehouse warehouse, Part part, int quantityOfPart);

    void changeTheQuantity(WarehousePart warehousePart, int quantityOfPart);

    void removePartOfThisType(WarehousePart warehousePart, int quantityOfPart);

    void deletePartOfThisType(WarehousePart warehousePart);
}

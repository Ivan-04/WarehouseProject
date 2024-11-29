package com.example.warehouseproject.services;

import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.Warehouse;
import com.example.warehouseproject.models.WarehousePart;
import com.example.warehouseproject.models.dtos.WarehousePartOutput;
import com.example.warehouseproject.repositories.WarehousePartsRepository;
import com.example.warehouseproject.services.contracts.WarehousePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehousePartServiceImpl implements WarehousePartService {

    private final WarehousePartsRepository warehousePartsRepository;
    private final ConversionService conversionService;


    @Override
    public List<WarehousePartOutput> findAllWarehousesParts() {
        List<WarehousePart> warehousesParts = warehousePartsRepository.findAll();
        return warehousesParts.stream().map(warehouseParts -> conversionService.convert(warehouseParts, WarehousePartOutput.class))
                .collect(Collectors.toList());
    }

    @Override
    public WarehousePart findWarehousePart(Warehouse warehouse, Part part) {

        return warehousePartsRepository.findByWarehouseAndPart(warehouse, part);
    }

    @Override
    public void createWarehouse(Warehouse warehouse, Part part, int quantityOfPart) {

        WarehousePart warehousePart = WarehousePart.builder()
                .warehouse(warehouse)
                .part(part)
                .quantity(quantityOfPart)
                .build();

        warehousePartsRepository.save(warehousePart);
    }

    @Override
    public void changeTheQuantity(WarehousePart warehousePart, int quantityOfPart) {
        warehousePart.setQuantity(warehousePart.getQuantity() + quantityOfPart);
        warehousePartsRepository.save(warehousePart);
    }

}
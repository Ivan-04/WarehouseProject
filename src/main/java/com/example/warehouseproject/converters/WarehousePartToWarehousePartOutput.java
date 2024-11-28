package com.example.warehouseproject.converters;

import com.example.warehouseproject.models.Warehouse;
import com.example.warehouseproject.models.WarehousePart;
import com.example.warehouseproject.models.dtos.WarehouseOutput;
import com.example.warehouseproject.models.dtos.WarehouseOutputId;
import com.example.warehouseproject.models.dtos.WarehousePartOutput;
import com.example.warehouseproject.repositories.PartRepository;
import com.example.warehouseproject.repositories.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WarehousePartToWarehousePartOutput implements Converter<WarehousePart, WarehousePartOutput> {

    private final WarehouseRepository warehouseRepository;
    private final PartRepository partRepository;

    @Override
    public WarehousePartOutput convert(WarehousePart warehousePart) {
        return WarehousePartOutput.builder()
                .warehouseName(warehouseRepository.findWarehouseEntityByWarehouseId(warehousePart.getWarehouse()
                        .getWarehouseId()).getName())
                .partName(partRepository.findPartEntityByPartId(warehousePart.getPart().getPartId()).getTitle())
                .quantity(warehousePart.getQuantity())
                .build();
    }

}

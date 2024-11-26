package com.example.warehouseproject.converters;

import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.Warehouse;
import com.example.warehouseproject.models.dtos.UserOutput;
import com.example.warehouseproject.models.dtos.WarehouseOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WarehouseToWarehouseOutput implements Converter<Warehouse, WarehouseOutput> {

    @Override
    public WarehouseOutput convert(Warehouse warehouse) {
        return WarehouseOutput.builder()
                .name(warehouse.getName())
                .build();
    }

}

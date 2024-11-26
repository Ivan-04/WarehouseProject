package com.example.warehouseproject.converters;

import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.Warehouse;
import com.example.warehouseproject.models.dtos.PartOutputId;
import com.example.warehouseproject.models.dtos.WarehouseOutputId;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WarehouseToWarehouseOutputId implements Converter<Warehouse, WarehouseOutputId> {

    @Override
    public WarehouseOutputId convert(Warehouse warehouse) {
        return WarehouseOutputId.builder()
                .warehouseId(warehouse.getWarehouseId())
                .build();
    }

}

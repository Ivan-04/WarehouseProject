package com.example.warehouseproject.converters;

import com.example.warehouseproject.models.WarehouseLog;
import com.example.warehouseproject.models.dtos.WarehouseLogOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WarehouseLogToWarehouseLogOutput implements Converter<WarehouseLog, WarehouseLogOutput> {

    @Override
    public WarehouseLogOutput convert(WarehouseLog warehouseLog) {
        return WarehouseLogOutput.builder()
                .email(warehouseLog.getUser().getEmail())
                .warehouseName(warehouseLog.getWarehouse().getName())
                .action(warehouseLog.getAction().name())
                .quantity(warehouseLog.getQuantity())
                .localDateTime(warehouseLog.getTimestamp())
                .build();
    }
}

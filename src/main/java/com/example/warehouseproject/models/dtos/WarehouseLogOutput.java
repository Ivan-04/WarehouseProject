package com.example.warehouseproject.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseLogOutput {

    private String email;

    private String warehouseName;

    private String partTitle;

    private String action;

    private int quantity;

    private LocalDateTime localDateTime;
}

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
public class PartOutput {

    private String title;

    private String description;

    private double price;

    private LocalDateTime createdAt;
}

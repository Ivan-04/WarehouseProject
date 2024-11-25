package com.example.warehouseproject.converters;

import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.PartOutput;
import com.example.warehouseproject.models.dtos.UserOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PartToPartOutput implements Converter<Part, PartOutput> {

    @Override
    public PartOutput convert(Part part) {
        return PartOutput.builder()
                .title(part.getTitle())
                .description(part.getDescription())
                .price(part.getPrice())
                .createdAt(part.getCreatedAt())
                .build();
    }
}

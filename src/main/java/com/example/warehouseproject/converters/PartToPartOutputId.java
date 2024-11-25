package com.example.warehouseproject.converters;

import com.example.warehouseproject.models.Line;
import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.dtos.LineOutput;
import com.example.warehouseproject.models.dtos.LineOutputId;
import com.example.warehouseproject.models.dtos.PartOutputId;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PartToPartOutputId implements Converter<Part, PartOutputId> {

    @Override
    public PartOutputId convert(Part part) {
        return PartOutputId.builder()
                .partId(part.getPartId())
                .build();
    }

}

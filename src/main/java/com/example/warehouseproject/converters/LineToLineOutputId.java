package com.example.warehouseproject.converters;

import com.example.warehouseproject.models.Line;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.LineOutputId;
import com.example.warehouseproject.models.dtos.UserOutputId;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LineToLineOutputId implements Converter<Line, LineOutputId> {

    @Override
    public LineOutputId convert(Line line) {
        return LineOutputId.builder()
                .id(line.getLineId())
                .build();
    }

}

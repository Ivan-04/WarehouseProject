package com.example.warehouseproject.services;

import com.example.warehouseproject.helpers.PermissionHelper;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.WarehouseLog;
import com.example.warehouseproject.models.dtos.WarehouseLogInput;
import com.example.warehouseproject.models.dtos.WarehouseLogOutput;
import com.example.warehouseproject.repositories.WarehouseLogRepository;
import com.example.warehouseproject.services.contracts.WarehouseLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WarehouseLogServiceImpl implements WarehouseLogService {

    private final WarehouseLogRepository warehouseLogRepository;
    private final ConversionService conversionService;

    @Override
    public List<WarehouseLogOutput> findAllWarehouseLogs(User user){
        PermissionHelper.isOwnerOrManager(user);
        List<WarehouseLog> listOFLogs = warehouseLogRepository.findAll();
        return listOFLogs.stream().map(log -> conversionService.convert(log, WarehouseLogOutput.class))
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseLogOutput findWarehouseLogById(int id, User user){
        PermissionHelper.isOwnerOrManager(user);
        WarehouseLog log = warehouseLogRepository.findByLogId(id);
        return conversionService.convert(log, WarehouseLogOutput.class);
    }

    @Override
    public void createWarehouseLog(WarehouseLogInput warehouseLogInput){

        WarehouseLog warehouseLog = WarehouseLog.builder()
                .user(warehouseLogInput.getUser())
                .warehouse(warehouseLogInput.getWarehouse())
                .part(warehouseLogInput.getPart())
                .action(warehouseLogInput.getAction())
                .quantity(warehouseLogInput.getQuantity())
                .timestamp(LocalDateTime.now())
                .build();

        warehouseLogRepository.save(warehouseLog);

    }


}

package com.example.warehouseproject.services.contracts;

import com.example.warehouseproject.models.Action;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.WarehouseLog;
import com.example.warehouseproject.models.dtos.WarehouseLogInput;
import com.example.warehouseproject.models.dtos.WarehouseLogOutput;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface WarehouseLogService {
    Page<WarehouseLog> getWarehouseLogWithFilters(String email, String partTitle, Integer quantity, String warehouseName, Action action, String description, LocalDateTime localDateTime, int page, int size, String sortBy, String sortDirection);

    List<WarehouseLogOutput> findAllWarehouseLogs(User user);

    WarehouseLogOutput findWarehouseLogById(int id, User user);

    void createWarehouseLog(WarehouseLogInput warehouseLogInput);
}

package com.example.warehouseproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "warehouse_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int logId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "part_id", nullable = false)
    private Part part;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Action action;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "description")
    private String description;
}

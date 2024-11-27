package com.example.warehouseproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lineparts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @ManyToOne
    @JoinColumn(name = "line_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "part_id", nullable = false)
    private Part part;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}

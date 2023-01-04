package com.example.cardealer.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "parts")
public class Part extends BaseEntity {

    private String name;

    private BigDecimal price;

    private int quantity;

    @ManyToOne
    private Supplier supplier;
}

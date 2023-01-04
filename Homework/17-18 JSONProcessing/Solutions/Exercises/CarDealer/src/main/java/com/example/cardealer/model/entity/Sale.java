package com.example.cardealer.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {

    @OneToOne
    private Car car;

    @OneToOne
    private Customer customer;

    @Column(name = "discount")
    private BigDecimal discountPercentage;
}

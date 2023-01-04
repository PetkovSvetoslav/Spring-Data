package com.example.cardealer.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    private String name;

    private LocalDateTime birthDate;

    private boolean isYoungDriver;
}

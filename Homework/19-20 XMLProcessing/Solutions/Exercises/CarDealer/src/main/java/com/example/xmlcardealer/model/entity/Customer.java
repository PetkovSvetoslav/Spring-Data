package com.example.xmlcardealer.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    private String name;

    private LocalDateTime birthDate;

    private boolean isYoungDriver;
}

package com.example.xmlcardealer.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity {

    private String name;

    private boolean isImporter;
}

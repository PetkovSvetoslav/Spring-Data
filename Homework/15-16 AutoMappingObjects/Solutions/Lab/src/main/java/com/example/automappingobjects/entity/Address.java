package com.example.automappingobjects.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String country;

    @NonNull
    private String city;

    @NonNull
    private String details;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id)
                && country.equals(address.country)
                && city.equals(address.city)
                && details.equals(address.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, details);
    }
}

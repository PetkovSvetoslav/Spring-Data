package entitties.vehicles;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
// With SINGLE_TABLE strategy don't use @Table annotation in the classes that inherit it.
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Vehicle {
    private Long id;
    private String model;
    private BigDecimal price;
    private String fuelType;

    protected Vehicle() {
    }

    protected Vehicle(String model, BigDecimal price, String fuelType) {
        this.model = model;
        this.price = price;
        this.fuelType = fuelType;
    }

    @Id
    // Use TABLE strategy when the inheritance type is TABLE_PER_CLASS
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "fuel_type")
    public String getFuelType() {
        return this.fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    // They only work if we use find(), but not for persist();
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "   id = " + id + System.lineSeparator() +
                "   model = " + model + System.lineSeparator() +
                "   price = " + price + System.lineSeparator() +
                "   fuelType = " + fuelType;
    }
}
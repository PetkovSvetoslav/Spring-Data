package entitties.vehicles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "trucks")
public class Truck extends Vehicle {
    private Double loadCapacity;

    public Truck() {
    }

    public Truck(String model, BigDecimal price, String fuelType, Double loadCapacity) {
        super(model, price, fuelType);
        this.loadCapacity = loadCapacity;
    }

    @Column(name = "load_capacity")
    public Double getLoadCapacity() {
        return this.loadCapacity;
    }

    public void setLoadCapacity(Double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    @Override
    public String toString() {
        return "Truck:" + System.lineSeparator() +
                super.toString() + System.lineSeparator() +
                "   loadCapacity = " + loadCapacity;
    }
}
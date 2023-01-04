package entitties.vehicles;

import entitties.Company;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "planes")
public class Plane extends Vehicle {
    private Integer passengerCapacity;

    public Plane() {
    }

    public Plane(String model, BigDecimal price, String fuelType, Integer passengerCapacity) {
        super(model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
    }

    @Column(name = "passenger_capacity")
    public Integer getPassengerCapacity() {
        return this.passengerCapacity;
    }

    public void setPassengerCapacity(Integer passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public String toString() {
        return "Plane:" + System.lineSeparator() +
                super.toString() + System.lineSeparator() +
                "   passengerCapacity = " + passengerCapacity;
    }
}
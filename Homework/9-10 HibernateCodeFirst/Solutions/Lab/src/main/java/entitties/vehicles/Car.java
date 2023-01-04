package entitties.vehicles;

import entitties.Driver;
import entitties.PlateNumber;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car extends Vehicle {
    private Integer seats;
    private PlateNumber plateNumber;
    private Set<Driver> drivers;

    public Car() {
    }

    public Car(String model, BigDecimal price, String fuelType, Integer seats) {
        super(model, price, fuelType);
        this.seats = seats;
    }

    public Car(String model, BigDecimal price, String fuelType, Integer seats, PlateNumber plateNumber, Set<Driver> drivers) {
        super(model, price, fuelType);
        this.seats = seats;
        this.plateNumber = plateNumber;
        this.drivers = drivers;
    }

    public Integer getSeats() {
        return this.seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
    public PlateNumber getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(PlateNumber plateNumber) {
        this.plateNumber = plateNumber;
    }

    @ManyToMany(mappedBy = "cars")
    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }

    @Override
    public String toString() {
        return "Car:" + System.lineSeparator() +
                super.toString() + System.lineSeparator() +
                "   seats = " + seats + System.lineSeparator() +
                plateNumber;
    }
}
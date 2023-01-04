package entitties;

import entitties.vehicles.Car;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "plate_numbers")
public class PlateNumber {
    private long id;
    private String plateNumber;
    private Car car;

    public PlateNumber() {
    }

    public PlateNumber(String plateNumber, Car car) {
        this.plateNumber = plateNumber;
        this.car = car;
    }

    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "plate_number")
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlateNumber that = (PlateNumber) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PlateNumber:" + System.lineSeparator() +
                "   id = " + id + System.lineSeparator() +
                "   plateNumber = " + plateNumber;
    }
}
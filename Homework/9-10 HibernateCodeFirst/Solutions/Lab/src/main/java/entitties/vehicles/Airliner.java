package entitties.vehicles;

import entitties.Company;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "airliners")
public class Airliner extends Plane {
    private Company company;

    public Airliner() {
    }

    public Airliner(String model, BigDecimal price, String fuelType, Integer passengerCapacity, Company company) {
        super(model, price, fuelType, passengerCapacity);
        this.company = company;
        this.company.addPlane(this);
    }

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
import entitties.Company;
import entitties.PlateNumber;
import entitties.vehicles.Airliner;
import entitties.vehicles.Car;
import entitties.vehicles.Plane;
import entitties.vehicles.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("vehicles");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Car car = new Car("Audi A8", new BigDecimal(5_500), "diesel", 5);
        Truck truck = new Truck("Fuso Canter", new BigDecimal(120_000), "gasoline", 5.5);
        PlateNumber plateNumber = new PlateNumber("C3020MH", car);

        entityManager.getTransaction().begin();
        entityManager.persist(car);
        entityManager.persist(truck);
        entityManager.persist(plateNumber);
        entityManager.getTransaction().commit();

        Car foundedCar = entityManager.find(Car.class, 1L);
        System.out.println("Found " + foundedCar);

        Truck foundedTruck = entityManager.find(Truck.class, 2L);
        System.out.println("Found " + foundedTruck);

        Company airbus = new Company("Airbus");
        Airliner airliner = new Airliner("A380", new BigDecimal(1_560_000), "gasoline", 260, airbus);
        Airliner airliner2 = new Airliner("A340", new BigDecimal(2_430_000), "gasoline", 320, airbus);

        Company boeing = new Company("Boeing");
        Airliner airliner3 = new Airliner("767", new BigDecimal(3_180_000), "gasoline", 620, boeing);
        Airliner airliner4 = new Airliner("757", new BigDecimal(2_790_000), "gasoline", 440, boeing);

        entityManager.getTransaction().begin();
        entityManager.persist(airbus);
        entityManager.persist(airliner);
        entityManager.persist(airliner2);
        entityManager.persist(boeing);
        entityManager.persist(airliner3);
        entityManager.persist(airliner4);
        entityManager.getTransaction().commit();

        Set<Airliner> planes = entityManager.find(Company.class, 2L).getPlanes();
        for (Airliner plane : planes) {
            System.out.println(plane);
        }

    }
}
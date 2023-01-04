import university_system.Course;
import university_system.Student;
import university_system.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;

import static factory.MyPersistence.createEntityManagerFactory;

public class Main {
    private final static String PERSISTENCE_UNIT_NAME = "persist";

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = createEntityManagerFactory(PERSISTENCE_UNIT_NAME, "exercise");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // 2. Sales Database
        /*Sale sale = new Sale();
        Product product = new Product("Potato", 2.00, new BigDecimal("4.25"));
        product.getSales().add(sale);
        sale.setProduct(product);

        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();*/

        // 3. university System
        Course course = new Course("Spring Data", "Well Spring data is being studied", new Date(), new Date(), 27);
        Student student = new Student("Dragan", "Velkov", "088", 4.69F, 296);
        Teacher teacher = new Teacher("Ivayla", "Georgieva", "014314", "iva@abv.bg", 10.56, course);
        course.getTeachers().add(teacher);

        entityManager.getTransaction().begin();
        entityManager.persist(teacher);
        entityManager.persist(student);
        entityManager.getTransaction().commit();
    }
}
package course.springdata.jpaintro;

import course.springdata.jpaintro.entity.Student;
import course.springdata.jpaintro.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    @SuppressWarnings(value = "unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
        EntityManager em = emf.createEntityManager();

        // Create and save an entity
        em.getTransaction().begin();
        String name = reader.readLine();
        Student student = new Student(name);
        em.persist(student);
        em.getTransaction().commit();

        // Get entity by id
        em.getTransaction().begin();
        long id = Long.parseLong(reader.readLine());
        Student found = em.find(Student.class, id);
        System.out.println(found);
        em.getTransaction().commit();

        // Select with JPA query
        em.getTransaction().begin();
        em.createQuery("SELECT s FROM Student AS s WHERE s.name LIKE :name")
                .setParameter("name", "G%")
                .getResultList().forEach(System.out::println);
        em.getTransaction().commit();

        // Merge entities
        em.getTransaction().begin();
        em.detach(found);
        found.setName("Peter");
        Student merge = em.merge(found);
        System.out.println(found == merge); //false
        em.getTransaction().commit();

        // Remove entity
        em.getTransaction().begin();
        Student removed = em.find(Student.class, id);
        em.remove(removed);
        System.out.println("Removed entity:");
        System.out.println(removed);
        em.getTransaction().commit();

        em.close();
    }
}

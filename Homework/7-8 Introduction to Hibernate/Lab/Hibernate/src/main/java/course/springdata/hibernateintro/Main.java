package course.springdata.hibernateintro;

import course.springdata.hibernateintro.entity.Address;
import course.springdata.hibernateintro.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Create Hibernate configuration
        Configuration configuration = new Configuration();
        configuration.configure();

        // Create SessionFactory
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Create Session
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Address address1 = new Address("Bulgaria", "Plovdiv", "Ivan Vazov");
        Address address2 = new Address("Bulgaria", "Sofia", "Peyo Yavorov");

        session.save(address1);
        session.save(address2);

        Student george = new Student("George", address2);
        Student andrey = new Student("Andrey");
        Student misho = new Student("Misho", address1);

        session.save(george);
        session.save(andrey);
        session.save(misho);
        session.getTransaction().commit();

//        // Persist an entity
//        String name = reader.readLine();
//        Student student = new Student(name);
//        session.beginTransaction();
//        session.save(student);
//        session.getTransaction().commit();
//
//        // Read entity by Id
//        session.beginTransaction();
//        session.setHibernateFlushMode(FlushMode.MANUAL);
//        long id = Long.parseLong(reader.readLine());
//        Student result = session.get(Student.class, id, LockMode.READ);
//        // Student result = session.byId(Student.class).load(id);
//        session.getTransaction().commit();
//        System.out.println(result);
//
//        // List all students using HQL
//        session.createQuery("FROM Student ", Student.class)
//                .setFirstResult(5)
//                .setMaxResults(10)
//                .stream().forEach(Syste`m.out::println);

//        session.beginTransaction();
//
//        session.createQuery("FROM Student WHERE name = :name", Student.class)
//                .setParameter("name", "Angel Velkov")
//        .stream().forEach(System.out::println);
//
//        session.getTransaction().commit();
//
//        session.beginTransaction();
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Student> query = cb.createQuery(Student.class);
//        Root<Student> Student_ = query.from(Student.class);
//        query.select(Student_).where(cb.like(Student_.get("name"), "D%"));
//        session.createQuery(query)
//                .getResultStream()
//                .forEach(System.out::println);
//        session.getTransaction().commit();
//
//        // Close Session
//        session.close();
    }
}


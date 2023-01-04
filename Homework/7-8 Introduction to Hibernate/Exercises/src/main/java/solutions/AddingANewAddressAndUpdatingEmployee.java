package solutions;

import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class AddingANewAddressAndUpdatingEmployee {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
// създаваме EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
//създаваме новият адрес и го присвояваме на променлива, чрез функция за създаване
        String addressText = "Vitoshka 15";
        Address address = createAddress(em, addressText);
//взимаме името и го тримваме след което го сетваме в куери
        String lastName = reader.readLine().trim();
        List<Employee> employees = em.createQuery(
                "SELECT e FROM Employee AS e WHERE e.lastName = :lastName", Employee.class)
                .setParameter("lastName", lastName)
                .getResultList();

//отваряме/взимаме "транзакция"
        em.getTransaction().begin();
// обхождаме, сетваме адреса и комитваме/присвояваме
        employees.forEach(e -> e.setAddress(address));
        em.getTransaction().commit();

//затваряме връзката
        em.close();
    }

    private static Address createAddress(EntityManager em, String addressText) {
        Address address = new Address();
        address.setText(addressText);

        em.getTransaction().begin();
        em.persist(address);
        em.getTransaction().commit();

        return address;
    }
}

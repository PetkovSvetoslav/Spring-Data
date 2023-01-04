package solutions;

import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class AddingWithEmployeeCount {
    public static void main(String[] args) {
        // създаваме EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        //създаваме лист от Employee от куери върху еm с нужните по заданието критерии
        List<Address> addresses = em.createQuery("SELECT a " +
                "FROM Address AS a " +
                "ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList();

      //обхождаме адресите и принтираме резултата
        for (Address address : addresses) {
            System.out.printf("%s, %s - %d employees%n",
                    address.getText(),
                    address.getTown() == null
                            ? "Unknown"
                            : address.getTown().getName(),
                    address.getEmployees().size());
        }
        //затваряме връзката
        em.close();
    }
}
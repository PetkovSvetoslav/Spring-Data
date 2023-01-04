package solutions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class EmployeesWithSalaryOver50000 {
    public static void main(String[] args) {
//създаваме EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
//създаваме лист от Employee от куери върху еm в което взимама всички със заплата над 5000
        List<String> employeesFirstName = em.createQuery(
                "SELECT e.firstName " +
                        "FROM Employee AS e " +
                        "WHERE e.salary > 50000", String.class)
                .getResultList();
//отпечатваме имената от листа
        employeesFirstName.forEach(System.out::println);
//затваряме връзката
        em.close();
    }
}
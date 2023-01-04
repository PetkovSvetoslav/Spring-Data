package solutions;

import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class IncreaseSalaries {
    public static void main(String[] args) {
// създаваме EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

//създаваме лист с статична информация
        List<String> departmentNames = List.of("Engineering", "Tool Design", "Marketing", "Information Services");
//създаваме лист от Employee от куери върху еm в което
        List<Employee> employees = em.createQuery("SELECT e " +
                        "FROM Employee AS e " +
                        "WHERE e.department.name IN (:departmentNames)", Employee.class)
                .setParameter("departmentNames", departmentNames)
                .getResultList();

//отваряме връзките
        em.getTransaction().begin();
        employees.forEach(em::detach);
//обхождаме емплоитата и сетваме заплатата
        for (Employee employee : employees) {
            employee.setSalary(employee.getSalary().multiply(BigDecimal.valueOf(1.12)));
        }

        employees.forEach(em::merge);
        em.flush();

        em.getTransaction().commit();
//създаваме стрингбилдър
        StringBuilder output = new StringBuilder();
//обхождаме емплоито и пълниме стрингбилтъра
        employees.forEach(e ->
                output.append(String.format("%s %s ($%.2f)%n",
                        e.getFirstName(),
                        e.getLastName(),
                        e.getSalary()))
        );

//печатаме резултата
        System.out.println(output);
//затваряме връзките
        emf.close();
        em.close();
    }
}
package solutions;

import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class FindLatest10Projects {
    public static void main(String[] args) {
// създаваме EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
//създаваме лист от Employee от куери върху еm в което
        List<Project> projects = em.createQuery("SELECT p FROM Project AS p ORDER BY p.name", Project.class)
                .setMaxResults(10)
                .getResultList();

        StringBuilder output = new StringBuilder();
 //обхождаме проектите и пълним стрингбилтъра
        for (Project project : projects) {
            output.append("Project name: ").append(project.getName())
                    .append(System.lineSeparator());
            output.append("        Project Description: ").append(project.getDescription())
                    .append(System.lineSeparator());
            output.append("        Project Start Date: ").append(project.getStartDate())
                    .append(System.lineSeparator());
            output.append("        Project End Date: ").append(project.getEndDate())
                    .append(System.lineSeparator());
        }
//  печатаме резултата
        System.out.println(output.toString().trim());
//затваряме връзката
        em.close();
    }
}
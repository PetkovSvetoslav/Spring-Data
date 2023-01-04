package solutions;

import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ChangeCasing {
    public static void main(String[] args) {
//        създаваме EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
//създаваме лист от куери на градовете с имена над 5 символа
        List<Town> towns = em.createQuery(
                "SELECT t FROM Town AS t WHERE LENGTH(t.name) >= 5", Town.class
        ).getResultList();
//ОТВАРЯМЕ ВРЪЗКАТА/потока  към базата данни "soft_uni"
        em.getTransaction().begin();
//отделяме ги от базата
        towns.forEach(em::detach);
//обхождаме градовете от листа с градове и сетваме името на големи букви
      for (Town town : towns) {
            town.setName(town.getName().toUpperCase());
        }
        towns.forEach(em::merge);
        em.flush();
//къмитваме/предаваме на  транзакцията и затваряме връзката
        em.getTransaction().commit();
        em.close();
    }
}
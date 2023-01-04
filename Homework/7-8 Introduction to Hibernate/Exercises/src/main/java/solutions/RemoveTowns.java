package solutions;

import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class RemoveTowns {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
// създаваме EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();


        em.getTransaction().begin();

        String townName = reader.readLine();
        Town town = em.createQuery("SELECT t FROM Town AS t " +
                "WHERE t.name = :townName", Town.class)
                .setParameter("townName", townName)
                .getSingleResult();

        List<Address> addressesToBeDeleted = em.createQuery(
                "SELECT a FROM Address AS a " +
                        "WHERE a.town = :town", Address.class)
                .setParameter("town", town)
                .getResultList();

        em.createQuery("UPDATE Employee AS e " +
                "SET e.address.id = NULL " +
                "WHERE e.address IN (:addresses)")
                .setParameter("addresses", addressesToBeDeleted)
                .executeUpdate();

        int deletedAddresses = em.createQuery("DELETE FROM Address AS a " +
                "WHERE a.town = :town")
                .setParameter("town", town)
                .executeUpdate();
        em.getTransaction().commit();

        System.out.println(deletedAddresses);
//затваряме връзките
        em.close();
        emf.close();
    }
}
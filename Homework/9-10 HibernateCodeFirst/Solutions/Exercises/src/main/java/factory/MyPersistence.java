package factory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public abstract class MyPersistence {
    public static EntityManagerFactory createEntityManagerFactory(String persistenceUnitName, String database) {
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/" + database + "?createDatabaseIfNotExist=true");
        return Persistence.createEntityManagerFactory(persistenceUnitName, properties);
    }
}
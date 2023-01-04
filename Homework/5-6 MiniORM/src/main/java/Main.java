import entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws SQLException, ParseException, IllegalAccessException, URISyntaxException, ClassNotFoundException, InvocationTargetException, InstantiationException, NoSuchMethodException {

MyConnector.createConnection("root", "Baron", "soft_uni");
        Connection connection = MyConnector.getConnection();

        EntityManager<User> userManager = new EntityManager<>(connection);
        User user = new User("Baron", 38);

        userManager.persist(user);



    }
}

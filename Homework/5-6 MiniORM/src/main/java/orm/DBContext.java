package orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface DBContext<E> {
    boolean persist(E entity) throws IllegalAccessException, SQLException, ParseException;

    Iterable<E> find(Class<E> table) throws SQLException,
            InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException;

    List<E> find(Class<E> table, String where) throws SQLException,
            IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    E findFirst(Class<E> table) throws SQLException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException;

    E findFirst(Class<E> table, String where) throws SQLException,
            IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    E findById(Class<E> table, int id) throws SQLException,
            IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    boolean delete(Class<E> table, int id) throws SQLException;

    void doCreate(Class<E> entity) throws SQLException;

    void doAlter(Class<E> entity) throws SQLException;

    void doDelete(E entity) throws SQLException, IllegalAccessException;
}

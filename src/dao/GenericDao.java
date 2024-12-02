package dao;

import connection.ConnectionDb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class GenericDao<T> {

    private Connection connection;

    public GenericDao() throws SQLException {
        ConnectionDb connectionDb = new ConnectionDb();
        connection = connectionDb.getConnection();
    }

    public abstract Optional<T> insert(T entity);
    public abstract List<T> findById(Integer id);
    public abstract List<T> findAll();
    public abstract List<String> findByName(String name);
    public abstract T update(T entity);
    public abstract void delete(Long id);
}

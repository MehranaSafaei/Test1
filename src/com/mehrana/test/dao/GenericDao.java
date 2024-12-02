package com.mehrana.test.dao;

import com.mehrana.test.connection.SimpleConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class GenericDao<T> {

    private final SimpleConnectionPool connectionPool; // نمونه برای Connection Pool

    // سازنده
    public GenericDao() {
        try {
            connectionPool = new SimpleConnectionPool(); // ایجاد اتصال Pool
        } catch (SQLException e) {
            throw new ExceptionInInitializerError("Failed to initialize com.mehrana.test.connection pool: " + e.getMessage());
        }
    }

    // متد برای گرفتن Connection
    protected Connection getConnection() {
        return connectionPool.getConnection();
    }

    // متد برای بازگرداندن Connection به Pool
    protected void releaseConnection(Connection connection) {
        connectionPool.releaseConnection(connection);
    }

    // متدهای انتزاعی برای عملیات پایگاه داده
    public abstract Optional<T> insert(T entity);

    public abstract List<T> findById(Integer id);

    public abstract List<T> findAll();

    public abstract List<String> findByName(String name);

    public abstract T update(T entity);

    public abstract void delete(Long id);
}

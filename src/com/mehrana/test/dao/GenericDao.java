package com.mehrana.test.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {


    Optional<T> insert(T entity) throws SQLException;

    Optional<T> findById(long id);

    List<T> findAll();

    T update(T entity);

    void delete(Long id);

}

package com.mehrana.test.dao;

import com.mehrana.test.connection.SimpleConnectionPool;
import com.mehrana.test.entity.Personnel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonnelDao  {

    private static final String INSERT = "INSERT INTO personnels (username, mobile, personnelCode) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE personnels SET username = ?, mobile = ?, personnelCode = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM personnels WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM personnels";
    private static final String SELECT_BY_ID = "SELECT * FROM personnels WHERE id = ?";
    private static final String SELECT_BY_USERNAME = "SELECT * FROM personnels WHERE username = ?";
    private static final String SELECT_BY_PERSONNEL_CODE =  "SELECT * FROM personnels WHERE personnelCode = ?";
//
//    private static final String INSERT = "INSERT INTO personnel (username, mobile, personnelCode) VALUES (?, ?, ?)";
//    private static final String UPDATE = "UPDATE personnel SET username = ?, mobile = ?, personnelCode = ? WHERE id = ?";
//    private static final String DELETE = "DELETE FROM personnel WHERE id = ?";
//    private static final String SELECT_ALL = "SELECT * FROM personnel";
//    private static final String SELECT_BY_ID = "SELECT * FROM personnel WHERE id = ?";
//    private static final String SELECT_BY_USERNAME = "SELECT * FROM personnel WHERE username = ?";
//    private static final String SELECT_BY_PERSONNEL_CODE =  "SELECT * FROM personnel WHERE personnelCode = ?";

    public PersonnelDao(){
        try {
            SimpleConnectionPool connectionPool = new SimpleConnectionPool(); // create connection pool
        } catch (SQLException e) {
            throw new ExceptionInInitializerError("Failed to initialize com.mehrana.test.connection pool: " + e.getMessage());
        }
    }

    public Optional<Personnel> insert(Personnel entity) throws SQLException {
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, entity.getUserName());
            preparedStatement.setString(2, entity.getMobile());
            preparedStatement.setLong(3, entity.getPersonnelCode());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                entity.setId(rs.getLong(1));

            }
            return Optional.of(entity);
        }
    }

    public Optional<Personnel> getById(long id) {
        Personnel personnel = null;
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Map the ResultSet to a Personnel object
                    personnel = mapResultSetToPersonnel(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(personnel); // Return an Optional
    }

    public List<Personnel> getAll() {
        List<Personnel> personnelList = new ArrayList<>();
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Personnel personnel = mapResultSetToPersonnel(resultSet);
                personnelList.add(personnel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnelList;
    }

    public List<Personnel> getByName(String username) {
        List<Personnel> personnelList = new ArrayList<>();
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_USERNAME)) {
            statement.setString(1, username); // Parameterize the query
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Map each row of the ResultSet to a Personnel object
                    Personnel personnel = mapResultSetToPersonnel(resultSet);
                    personnelList.add(personnel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }
        return personnelList; // Return the populated list
    }

    public Personnel update(Personnel entity) {
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {

            statement.setString(1, entity.getUserName());
            statement.setString(2, entity.getMobile());
            statement.setLong(3, entity.getPersonnelCode());
            statement.setLong(4, entity.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return entity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void delete(Long id) {
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {

            statement.setLong(1, id); // Set the ID parameter

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Personnel findByPersonnelCode(long personnelCode) {
        Personnel personnel = null;
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_PERSONNEL_CODE)) {

            statement.setLong(1, personnelCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    personnel = mapResultSetToPersonnel(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return personnel; // Return the found Personnel object or null if not found
    }

    // Helper method to map ResultSet to Personnel object
    private Personnel mapResultSetToPersonnel(ResultSet resultSet) throws SQLException {
        return new Personnel(
                resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("mobile"),
                resultSet.getLong("PersonnelCode")
        );
    }


}

package com.mehrana.test.dao;

import com.mehrana.test.connection.SimpleConnectionPool;
import com.mehrana.test.entity.Personnel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonnelDao extends GenericDao<Personnel> {

    private static final String INSERT = "INSERT INTO personnles (username, mobile, PersonnelCode) VALUES (?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM personnles WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM personnles";
    private static final String SELECT_BY_USERNAME = "SELECT * FROM personnles WHERE username = ?";
    private static final String UPDATE = "UPDATE personnles SET username = ?, mobile = ?, PersonnelCode = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM personnles WHERE id = ?";

    public PersonnelDao() throws SQLException {
    }

    @Override
    public Optional<Personnel> insert(Personnel entity) throws SQLException {
        try (Connection connection = SimpleConnectionPool.getConnection()) {
            assert connection != null;
            try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, entity.getUserName());
                statement.setString(2, entity.getMobile());
                statement.setLong(3, entity.getPersonnelCode());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(1));
                    }
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Personnel> findById(Integer id) {
        List<Personnel> personnelList = new ArrayList<>();
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Personnel personnel = mapResultSetToPersonnel(resultSet);
                    personnelList.add(personnel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnelList;
    }

    @Override
    public List<Personnel> findAll() {
        List<Personnel> personnelList = new ArrayList<>();
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Personnel personnel = mapResultSetToPersonnel(resultSet);
                personnelList.add(personnel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnelList;
    }

    @Override
    public List<Personnel> findByName(String username) {
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


    @Override
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

    @Override
    public void delete(Long id) {
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
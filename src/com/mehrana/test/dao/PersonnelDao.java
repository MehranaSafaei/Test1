package com.mehrana.test.dao;

import com.mehrana.test.connection.SimpleConnectionPool;
import com.mehrana.test.entity.Personnel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonnelDao extends GenericDao<Personnel> {

    private static final String INSERT = "INSERT INTO personnel (username, mobile, personnel_code) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE personnel SET username = ?, mobile = ?, personnel_code = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM personnel WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM personnel";
    private static final String SELECT_BY_ID = "SELECT * FROM personnel WHERE id = ?";
    private static final String SELECT_BY_USERNAME = "SELECT * FROM personnel WHERE username = ?";
    private static final String SELECT_BY_PERSONNEL_CODE =  "SELECT * FROM personnel WHERE personnel_code = ?";

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
    public Optional<Personnel> findById(long id) {
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

            statement.setLong(1, id); // Set the ID parameter
            int rowsAffected = statement.executeUpdate(); // Execute the DELETE query

            if (rowsAffected == 0) {
                System.out.println("No rows were deleted. ID might not exist.");
            }
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

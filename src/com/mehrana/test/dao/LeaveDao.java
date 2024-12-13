package com.mehrana.test.dao;

import com.mehrana.test.connection.SimpleConnectionPool;
import com.mehrana.test.entity.Leave;
import com.mehrana.test.entity.Personnel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class LeaveDao {

    private static final String INSERT = "INSERT INTO Leaves (startDate, endDate, description, personelId) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_USERNAME = "SELECT * FROM Leaves WHERE username = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM Leaves WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM Leaves";


    public LeaveDao() throws SQLException {
        try {
            SimpleConnectionPool connectionPool = new SimpleConnectionPool(); // create connection pool
        } catch (SQLException e) {
            throw new ExceptionInInitializerError("Failed to initialize com.mehrana.test.connection pool: " + e.getMessage());
        }
    }

    public Optional<Leave> insert(Leave entity) throws SQLException {
        if (entity.getPersonnelId() == null) {
            throw new IllegalArgumentException("Personnel ID cannot be null.");
        }

        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, java.sql.Date.valueOf(entity.getStartDate()));
            ps.setDate(2, java.sql.Date.valueOf(entity.getEndDate()));
            ps.setString(3, entity.getDescription());
            ps.setLong(4, entity.getPersonnelId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                }
            }
        }
        return Optional.of(entity);
    }

    public Optional<Leave> findById(long id) {
        Leave leave = null;
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    leave = mapResultSetToLeave(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(leave);
    }

    public List<Leave> findAll() {
        List<Leave> leaveList = new ArrayList<>();
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Leave leave = mapResultSetToLeave(resultSet);
                leaveList.add(leave);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaveList;
    }

    public List<Leave> selectByUsername(String username) throws SQLException {
        List<Leave> leaves = new ArrayList<>();
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_USERNAME)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Leave leave = mapResultSetToLeave(rs);
                    leaves.add(leave);
                }
            }
        }
        return leaves;
    }

    //
//    private Leave mapResultSetToLeave(ResultSet resultSet) throws SQLException {
//        Leave leave = new Leave();
//        leave.setId(resultSet.getInt("id"));
//        leave.setStartDate(resultSet.getDate("startDate").toLocalDate());
//        leave.setEndDate(resultSet.getDate("endDate").toLocalDate());
//        leave.setDescription(resultSet.getString("description"));
//        leave.setPersonnelId(resultSet.getLong("personnelId"));
//        return leave;
//    }
    private Leave mapResultSetToLeave(ResultSet resultSet) throws SQLException {
        Leave leave = new Leave();
        try {
            leave.setId(resultSet.getLong("id"));
            java.sql.Date startDate = resultSet.getDate("startDate");
            java.sql.Date endDate = resultSet.getDate("endDate");

//            if (startDate != null) {
//                leave.setStartDate(startDate.toLocalDate());
//            } else {
//                leave.setStartDate(null); // or use a default value
//            }

            // Handle null values
            //if
            leave.setStartDate(startDate != null ? startDate.toLocalDate() : null);
            //if
            leave.setEndDate(endDate != null ? endDate.toLocalDate() : null);

            leave.setDescription(resultSet.getString("description"));
            leave.setPersonnelId(resultSet.getLong("personnelId"));
        } catch (SQLException e) {
            // Log or print the error message
            System.out.println("Error while mapping result set to leave: " + e.getMessage());
            throw e; // Rethrow or handle the exception as needed
        }
        return leave;
    }

}

package com.mehrana.test.dao;

import com.mehrana.test.connection.SimpleConnectionPool;
import com.mehrana.test.entity.Leave;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class LeaveDao {

//
//    private String INSERT = "INSERT INTO Leaves (startDate, endDate, description, personelId) VALUES (?, ?, ?, ?)";
//    private String SELECT_BY_USERNAME = "SELECT * FROM Leaves WHERE username = ?";
//    private static final String SELECT_ALL = "SELECT * FROM Leaves";
//    private static final String SELECT_BY_ID = "SELECT * FROM Leaves WHERE id = ?";

    private String INSERT = "INSERT INTO Leave (startDate, endDate, description, personnelId) VALUES (?::date, ?::date, ?, ?)";
    private static final String UPDATE = "UPDATE leave SET startDate = ?, endDate = ?, description = ? WHERE id = ?";
    private String SELECT_BY_USERNAME = "SELECT * FROM Leave WHERE username = ?";
    private static final String SELECT_ALL = "SELECT * FROM Leave";
    private static final String SELECT_BY_ID = "SELECT * FROM Leave WHERE id = ?";
    private static final String SELECT_BY_PERSONNEL_ID = "SELECT * FROM leave WHERE personnelId = ?";


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

            ps.setDate(1, new java.sql.Date(entity.getStartDate().getTime()));
            ps.setDate(2, new java.sql.Date(entity.getEndDate().getTime()));
            ps.setString(3, entity.getDescription());
            ps.setLong(4, entity.getPersonnelId()); // Set the employee ID value directly

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                }
            }
        }
        return Optional.of(entity);
    }


    public Optional<Leave> getById(long id) {
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Leave leave = new Leave();
                leave.setStartDate(new Date(resultSet.getLong("startDate")));
                leave.setEndDate(new Date(resultSet.getLong("endDate")));
                leave.setDescription(resultSet.getString("description"));
                leave.setPersonnelId(resultSet.getLong("personnelId"));
                return Optional.of(leave);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public List<Leave> getAll() {
        List<Leave> leaveList = new ArrayList<>();
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Leave leave = new Leave();
                leave.setStartDate(new Date(resultSet.getLong("startDate")));
                leave.setEndDate(new Date(resultSet.getLong("endDate")));
                leave.setDescription(resultSet.getString("description"));
                leave.setPersonnelId(resultSet.getLong("personnelId"));
                leaveList.add(leave);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaveList;
    }


    public List<Leave> getByName(String name) {
        return List.of();
    }

    public Optional<Object> update(Optional<Leave> entity) throws SQLException {
        try(Connection connection = SimpleConnectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setString(1, entity.get().getDescription());
            preparedStatement.setDate(2, new java.sql.Date(entity.get().getStartDate().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(entity.get().getEndDate().getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }

    public void delete(Long id) {

    }

    public List<Leave> selectByUsername(String username) throws SQLException {
        List<Leave> leaves = new ArrayList<>();
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_USERNAME)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Leave leave = new Leave();
                leave.setId(rs.getInt(1));
                leave.setStartDate(rs.getDate(2));
                leave.setEndDate(rs.getDate(3));
                leave.setDescription(rs.getString(4));
                leave.setPersonnelId(rs.getLong(5));
                leaves.add(leave);
            }
        }
        return leaves;
    }


        public Optional<Leave> findLeaveByPersonnelCode(Long personnelCode) throws SQLException {
            Leave leave = null;

            try (Connection connection = SimpleConnectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SELECT_BY_PERSONNEL_ID)) {

                statement.setLong(1, personnelCode);

                ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {

                        leave = new Leave();
                        leave.setId((int) resultSet.getLong("id"));
                        leave.setPersonnelId(resultSet.getLong("personnelId"));
                        leave.setDescription(resultSet.getString("description"));
                        leave.setStartDate(resultSet.getDate("startDate"));
                        leave.setEndDate(resultSet.getDate("endDate"));
                    }

            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException("Error fetching leave information for personnelId: " + personnelCode, e);
            }

            return Optional.of(leave);
        }

    }


package com.mehrana.test.dao;

import com.mehrana.test.connection.SimpleConnectionPool;
import com.mehrana.test.entity.Leave;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class LeaveDao {

<<<<<<< HEAD
    private static final String INSERT = "INSERT INTO Leaves (startDate, endDate, description, personelId) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_USERNAME = "SELECT * FROM Leaves WHERE username = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM Leaves WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM Leaves";
=======
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
>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab


    public LeaveDao() throws SQLException {
        try {
            SimpleConnectionPool connectionPool = new SimpleConnectionPool(); // create connection pool
        } catch (SQLException e) {
            throw new ExceptionInInitializerError("Failed to initialize com.mehrana.test.connection pool: " + e.getMessage());
        }
    }
<<<<<<< HEAD
=======

>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab

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

<<<<<<< HEAD
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    leave = mapResultSetToLeave(resultSet);
                }
=======
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
>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
<<<<<<< HEAD
        return Optional.ofNullable(leave);
    }

    public List<Leave> findAll() {
=======
        return Optional.empty();
    }


    public List<Leave> getAll() {
>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab
        List<Leave> leaveList = new ArrayList<>();
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
<<<<<<< HEAD
                Leave leave = mapResultSetToLeave(resultSet);
=======
                Leave leave = new Leave();
                leave.setStartDate(new Date(resultSet.getLong("startDate")));
                leave.setEndDate(new Date(resultSet.getLong("endDate")));
                leave.setDescription(resultSet.getString("description"));
                leave.setPersonnelId(resultSet.getLong("personnelId"));
>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab
                leaveList.add(leave);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaveList;
<<<<<<< HEAD
=======
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

>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab
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

<<<<<<< HEAD
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
=======

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

>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab

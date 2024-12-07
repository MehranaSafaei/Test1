package com.mehrana.test.dao;

import com.mehrana.test.connection.SimpleConnectionPool;
import com.mehrana.test.entity.Leave;
import com.mehrana.test.entity.Personnel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LeaveDao extends GenericDao<Leave> {


    private String INSERT = "INSERT INTO Leaves (startDate, endDate, description, personelId) VALUES (?, ?, ?, ?)";
    private String SELECT_BY_USERNAME = "SELECT * FROM Leaves WHERE username = ?";

    @Override
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


    @Override
    public Optional<Personnel> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Leave> findAll() {
        return List.of();
    }

    @Override
    public List<Leave> findByName(String name) {
        return List.of();
    }

    @Override
    public Leave update(Leave entity) {
        return null;
    }

    @Override
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
}

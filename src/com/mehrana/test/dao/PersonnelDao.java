package com.mehrana.test.dao;

import com.mehrana.test.connection.SimpleConnectionPool;
import com.mehrana.test.entity.Personnel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

<<<<<<< HEAD
public class PersonnelDao {

    private static final String INSERT = "INSERT INTO personnel (username, mobile, personnelCode) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE personnel SET username = ?, mobile = ?, personnelCode = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM personnel WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM personnel";
    private static final String SELECT_BY_ID = "SELECT * FROM personnel WHERE id = ?";
    private static final String SELECT_BY_USERNAME = "SELECT * FROM personnel WHERE username = ?";
    private static final String SELECT_BY_PERSONNEL_CODE =  "SELECT * FROM personnel WHERE personnelCode = ?";

=======
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

>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab
    public PersonnelDao(){
        try {
            SimpleConnectionPool connectionPool = new SimpleConnectionPool(); // create connection pool
        } catch (SQLException e) {
            throw new ExceptionInInitializerError("Failed to initialize com.mehrana.test.connection pool: " + e.getMessage());
<<<<<<< HEAD
        }
    }


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
        return Optional.of(entity);
    }

    public Optional<Personnel> findById(long id) {
=======
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
>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab
        Personnel personnel = null;
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    personnel = mapResultSetToPersonnel(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(personnel);
    }

<<<<<<< HEAD
    public List<Personnel> findAll() {
=======
    public List<Personnel> getAll() {
>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab
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

<<<<<<< HEAD
    public List<Personnel> findByName(String username) {
=======
    public List<Personnel> getByName(String username) {
>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab
        List<Personnel> personnelList = new ArrayList<>();
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_USERNAME)) {
            statement.setString(1, username); // Parameterize the query
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

<<<<<<< HEAD
=======

>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab
    public void delete(Long id) {
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {

<<<<<<< HEAD
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
=======
            statement.setLong(1, id); // Set the ID parameter
>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab

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
            e.printStackTrace();
        }
        return personnel;
    }

    private Personnel mapResultSetToPersonnel(ResultSet resultSet) throws SQLException {
        return new Personnel(
                resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("mobile"),
                resultSet.getLong("PersonnelCode")
        );
    }
}

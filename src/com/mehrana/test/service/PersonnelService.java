package com.mehrana.test.service;

import com.mehrana.test.dao.PersonnelDao;
import com.mehrana.test.entity.Personnel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PersonnelService {
   PersonnelDao personnelDao = new PersonnelDao();

    public PersonnelService() throws SQLException {
    }

    public Optional<Personnel> insert(Personnel personnel) throws SQLException {
       return personnelDao.insert(personnel);
   }

    public List<Personnel> getListPersonnel() {
     return personnelDao.findAll();
    }

    public Personnel updatePersonnel(Personnel personnel) {
        return personnelDao.update(personnel);
    }

    public Personnel findPersonnelByCode(long personnelCode) {
        return personnelDao.findByPersonnelCode(personnelCode);
    }

    public Optional<Personnel> findById(long id) throws SQLException {
        return personnelDao.findById(id);
    }

    public void deleteById(long id) {
        personnelDao.delete(id);
    }

    public List<Personnel> findPersonnelByName(String name) {
        return personnelDao.findByName(name);
    }
}

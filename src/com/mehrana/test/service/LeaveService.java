package com.mehrana.test.service;

import com.mehrana.test.dao.LeaveDao;
import com.mehrana.test.dao.PersonnelDao;
import com.mehrana.test.entity.Leave;
import com.mehrana.test.entity.Personnel;

import java.sql.SQLException;
import java.util.Optional;

public class LeaveService {


    LeaveDao leaveDao = new LeaveDao();

    public LeaveService() {

    }

    public Optional<Leave> insert(Leave entity) throws SQLException {
        return leaveDao.insert(entity);
    }


}
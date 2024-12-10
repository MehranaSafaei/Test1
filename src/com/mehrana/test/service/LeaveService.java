package com.mehrana.test.service;

import com.mehrana.test.dao.LeaveDao;
import com.mehrana.test.entity.Leave;

import java.sql.SQLException;
import java.util.Optional;

public class LeaveService {


    LeaveDao leaveDao = new LeaveDao();

    public LeaveService() throws SQLException {

    }

    public Optional<Leave> insert(Leave entity) throws SQLException {
        return leaveDao.insert(entity);
    }

    public Optional<Object> update(Optional<Leave> entity) throws SQLException {
        return leaveDao.update(entity);
    }


    public Optional<Leave> findLeaveByPersonnelId(Long id) throws SQLException {
        return leaveDao.findLeaveByPersonnelCode(id);
    }
}

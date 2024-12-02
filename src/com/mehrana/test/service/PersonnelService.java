package com.mehrana.test.service;

import com.mehrana.test.dao.PersonnelDao;
import com.mehrana.test.entity.Personnel;

import java.util.Optional;

public class PersonnelService {
   PersonnelDao personnelDao = new PersonnelDao();

   public Optional<Personnel> insert(Personnel personnel) {
       return personnelDao.insert(personnel);
   }
}

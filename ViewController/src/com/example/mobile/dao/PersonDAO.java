package com.example.mobile.dao;

import com.example.mobile.dao.jpa.MAFSQLiteConnectionSource;
import com.example.mobile.model.Person;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import java.util.List;

import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.util.Utility;

public class PersonDAO {
    
    ConnectionSource connectionSource;

    
    public PersonDAO() {
        String Dir = AdfmfJavaUtilities.getDirectoryPathRoot(AdfmfJavaUtilities.ApplicationDirectory);
        System.out.println(Dir);
        String connStr = "jdbc:sqlite:" + Dir + "/sm.db";
        System.out.println(connStr);
        // create a connection source to our database
        try {
            connectionSource = new MAFSQLiteConnectionSource(connStr, "newPassword");
            
        } catch (SQLException e) {
            e.printStackTrace();
            Utility.ApplicationLogger.severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Person> findAll() throws SQLException {
        Dao<Person,String> personDao =
             DaoManager.createDao(connectionSource, Person.class);
                
        return personDao.queryForAll();
    }


    @Override
    protected void finalize() throws Throwable {
        if(connectionSource != null)
            connectionSource.close(); 
        super.finalize();
    }
}

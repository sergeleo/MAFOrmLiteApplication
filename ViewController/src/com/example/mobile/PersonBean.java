package com.example.mobile;

import com.example.mobile.dao.PersonDAO;
import com.example.mobile.model.Person;

import java.sql.SQLException;

import java.util.List;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.util.Utility;

public class PersonBean {
    private List<Person> persons;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public PersonBean() {
        PersonDAO personDAO = new PersonDAO();
        try {
            setPersons(personDAO.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
            Utility.ApplicationLogger.severe(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public void setPersons(List<Person> persons) {
        List<Person> oldPersons = this.persons;
        this.persons = persons;
        propertyChangeSupport.firePropertyChange("persons", oldPersons, persons);
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
}

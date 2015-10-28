package dao;

import model.Person;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IPersonDao {

    void addPerson(Person person);

    List<Person> getAllPersons();
}

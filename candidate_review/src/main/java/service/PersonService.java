package service;

import dao.IPersonDao;
import model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * Created by Peter.
 */

@Service(value = "personService")
public class PersonService {

    @Autowired
    IPersonDao personDao;

    public List<Person> getAll(){
        return(personDao.getAllPersons());
    }
}

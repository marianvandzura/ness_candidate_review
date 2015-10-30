package dao;

import model.Person;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IPersonDao {

    void addPerson(Person person);

    //annotation to return response as JSON
    //@ResponseBody
    List<Person> getAllPersons();
}

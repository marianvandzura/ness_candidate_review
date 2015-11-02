package service;

import dao.IPersonDao;
import model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by Peter.
 */
@Configuration
@EnableAsync
public class PersonAsyncService {

    @Autowired
    IPersonDao personDao;

    @Async
    public Future<List<Person>> getAll(){
        return new AsyncResult<List<Person>>(personDao.getAllPersons());
    }
}

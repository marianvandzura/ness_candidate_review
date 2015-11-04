package service;

import dao.IOptionsDao;
import model.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Peter on 4.11.2015.
 */
@Service
public class OptionService {

    @Autowired
    private IOptionsDao optionsDao;

    public List<Options> findAll() {
        return optionsDao.getAllOptiopns();
    }

    public Options save(final Options option) {
        return optionsDao.addOption(option);
    }

    public Options findById(final Integer id) {
        return optionsDao.findById(id);
    }

    public List<Options> findByQuestion(final Integer questionId) {
        return optionsDao.findOptionsForQuestion(questionId);
    }
}
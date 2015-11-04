package service;

<<<<<<< HEAD
import assemblers.OptionAssembler;
import dao.IOptionsDao;
import dao.IQuestionsDao;
import dto.OptionDto;
=======
import dao.IOptionsDao;
>>>>>>> refs/remotes/origin/master
import model.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
<<<<<<< HEAD
 * Created by Marian_Vandzura on 3.11.2015.
=======
 * Created by Peter on 4.11.2015.
>>>>>>> refs/remotes/origin/master
 */
@Service
public class OptionService {

    @Autowired
<<<<<<< HEAD
    IOptionsDao optionsDao;

    @Autowired
    OptionAssembler optionAssembler;

    @Autowired
    IQuestionsDao questionsDao;

    public Options addOption(final OptionDto optionDto){
        Options option =  optionAssembler.populateDomainFromDto(optionDto, questionsDao);
        return optionsDao.addOption(option);
    }

    public List<OptionDto> findOptionsByQuestionId (final Integer questionId) {
        return optionAssembler.extractDtoListFromDomain(optionsDao.findOptionsByQuestion(questionId));
=======
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
>>>>>>> refs/remotes/origin/master
    }
}

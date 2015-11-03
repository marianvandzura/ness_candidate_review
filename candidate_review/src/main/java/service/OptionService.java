package service;

import assemblers.OptionAssembler;
import dao.IOptionsDao;
import dao.IQuestionsDao;
import dto.OptionDto;
import model.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Marian_Vandzura on 3.11.2015.
 */
@Service
public class OptionService {

    @Autowired
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
    }
}

package service;

import assemblers.OptionAssembler;
import dao.IOptionsDao;
import dto.OptionDto;
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

    @Autowired
    OptionAssembler optionAssembler;

    /**
     * get all available options
     * @return List of options
     */
    public List<OptionDto> getAllOptions() {
        return optionAssembler.extractDtoFromDomain(optionsDao.getAllOptions());
    }

    /**
     * add option
     * @param option
     * @return added OptionDto object
     */
    public OptionDto addOption(final OptionDto option) {
        return optionAssembler.extractDtoFromDomain(optionsDao.addOption(optionAssembler.populateDomainFromDto(option)));
    }

    /**
     * delete option
     * @param option
     */
    public void deleteOption(final OptionDto option) {
        optionsDao.deleteOption(optionAssembler.populateDomainFromDto(option));
    }

    /**
     * find option based on option ID
     * @param id
     * @return OptionDto object with ID
     */
    public OptionDto findOptionById(final Integer id) {
        return optionAssembler.extractDtoFromDomain(optionsDao.findOptionById(id));
    }

    /**
     * get all options for question
     * @param questionId
     * @return List of options related to question with ID
     */
    public List<OptionDto> findOptionsByQuestionId(final Integer questionId) {
        return optionAssembler.extractDtoFromDomain(optionsDao.findOptionsByQuestionId(questionId));
    }
}

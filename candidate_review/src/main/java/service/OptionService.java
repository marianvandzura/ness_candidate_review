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

    public List<OptionDto> getAllOptions() {
        return optionAssembler.extractDtoFromDomain(optionsDao.getAllOptiopns());
    }

    public OptionDto addOption(final OptionDto option) {
        return optionAssembler.extractDtoFromDomain(optionsDao.addOption(optionAssembler.populateDomainFromDto(option)));
    }

    public OptionDto findOptionById(final Integer id) {
        return optionAssembler.extractDtoFromDomain(optionsDao.findOptionById(id));
    }

    public List<OptionDto> findOptionsByQuestionId(final Integer questionId) {
        return optionAssembler.extractDtoFromDomain(optionsDao.findOptionsByQuestionId(questionId));
    }
}

package assemblers;

import dao.ICategoriesDao;
import dao.IOptionsDao;
import dto.QuestionDto;
import model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class for assembling Question objects to DTO(Data Transprt Objects) from domain and vice versa.
 *
 * Created by Peter.
 */

@Component
public class QuestionAssembler {

    @Autowired
    ICategoriesDao categoriesDao;

    @Autowired
    CategoryAssembler categoryAssembler;

    @Autowired
    OptionAssembler optionAssembler;

    @Autowired
    IOptionsDao optionsDao;

    public QuestionDto extractDtoFromDomain(final Questions domain) {
        QuestionDto dto = new QuestionDto();
        dto.setId(domain.getQuestionId());
        dto.setCode(domain.getCode());
        dto.setImageUrl(domain.getImageUrl());
        dto.setLanguage(domain.getLanguage());
        dto.setLevel(domain.getLevel());
        dto.setQuestion(domain.getQuestion());
        dto.setType(domain.getType());
        dto.setOptions(optionAssembler.extractDtoFromDomain(optionsDao.findOptionsForQuestion(domain.getQuestionId())));
        dto.setCategory(categoryAssembler.extractDtoFromDomain(domain.getCategory()));
        return dto;
    }

    public Questions populateDomainFromDto(final QuestionDto dto) {
        Questions domain = new Questions();
        domain.setQuestionId(dto.getId());
        domain.setCode(dto.getCode());
        domain.setImageUrl(dto.getImageUrl());
        domain.setLanguage(dto.getLanguage());
        domain.setLevel(dto.getLevel());
        domain.setQuestion(dto.getQuestion());
        domain.setType(dto.getType());
        domain.setCategory(categoriesDao.findById(dto.getCategory().getId()));
        optionAssembler.populateDomainFromDto(dto.getOptions());
        return domain;
    }

    /**
     * Extract List of DTOs from domain.
     *
     * @param domain
     * @return extracted DTOs
     */
    public List<QuestionDto> extractDtosListFromDomain(final Collection<Questions> domain) {
        List<QuestionDto> questionDtoArrayList = new ArrayList<QuestionDto>();
        for (Questions question : domain) {
            questionDtoArrayList.add(extractDtoFromDomain(question));
        }
        return questionDtoArrayList;
    }
}

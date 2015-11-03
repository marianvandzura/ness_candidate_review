package assemblers;

import dao.IOptionsDao;
import dao.IQuestionsDao;
import dao.impl.QuestionsDao;
import dto.OptionDto;
import dto.QuestionDto;
import model.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.QuestionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for assembling Option objects to DTO(Data Transfer Objects) from domain and vice versa.
 *
 * Created by Marian_Vandzura on 3.11.2015.
 */
@Component
public class OptionAssembler {

    public OptionAssembler(){
        //default
    }

    /**
     * create OptionDto object from domain model Options
     * @param domain Options model
     * @return OptionDto object
     */
    public OptionDto extractDtoFromDomain(final Options domain) {
        OptionDto optionDto = new OptionDto();
        optionDto.setId(domain.getOptionId());
        optionDto.setOption(domain.getOption());
        optionDto.setQuestionId(domain.getQuestion().getQuestionId());
        optionDto.setTruth(domain.getTruth());
        return optionDto;
    }


    /**
     * create domain object from DTO
     * @param dto
     * @param questionsDao
     * @return domain object
     */
    public Options populateDomainFromDto(final OptionDto dto, IQuestionsDao questionsDao) {
        Options domain = new Options();
        domain.setOptionId(dto.getId());
        domain.setOption(dto.getOption());
        domain.setQuestion(questionsDao.findQuestionById(dto.getQuestionId()));
        domain.setTruth(dto.getTruth());
        return domain;
    }

    /**
     * Extract List of DTOs from domain.
     *
     * @param domain
     * @return extracted DTOs
     */
    public List<OptionDto> extractDtoListFromDomain(final List<Options> domain) {
        List<OptionDto> optionDtoArrayList = new ArrayList<OptionDto>();
        for (Options option : domain) {
            optionDtoArrayList.add(extractDtoFromDomain(option));
        }
        return optionDtoArrayList;
    }

}

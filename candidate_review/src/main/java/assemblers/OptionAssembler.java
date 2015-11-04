package assemblers;

<<<<<<< HEAD
import dao.IOptionsDao;
import dao.IQuestionsDao;
import dao.impl.QuestionsDao;
import dto.OptionDto;
import dto.QuestionDto;
import model.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.QuestionService;
=======
import dto.OptionDto;
import model.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
>>>>>>> refs/remotes/origin/master

import java.util.ArrayList;
import java.util.List;

/**
<<<<<<< HEAD
 * Class for assembling Option objects to DTO(Data Transfer Objects) from domain and vice versa.
 *
 * Created by Marian_Vandzura on 3.11.2015.
=======
 * Created by Peter on 4.11.2015.
>>>>>>> refs/remotes/origin/master
 */
@Component
public class OptionAssembler {

<<<<<<< HEAD
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
=======
    @Autowired
    private QuestionAssembler questionAssembler;

    public OptionDto extractDtoFromDomain(final Options domain) {
        OptionDto dto = new OptionDto();
        dto.setId(domain.getOptionId());
        dto.setOption(domain.getOption());
        dto.setTruth(domain.getTruth());
        dto.setQuestion(questionAssembler.extractDtoFromDomain(domain.getQuestion()));
        return dto;
    }

    public List<OptionDto> extractDtoFromDomain(final List<Options> domains) {
        List<OptionDto> dtos = new ArrayList<OptionDto>();
        for(Options domain : domains) {
            dtos.add(this.extractDtoFromDomain(domain));
        }
        return dtos;
    }
    public Options populateDomainFromDto(final OptionDto dto) {
        Options domain = new Options();
        domain.setOptionId(dto.getId());
        domain.setOption(dto.getOption());
        domain.setTruth(dto.getTruth());
        domain.setQuestion(questionAssembler.populateDomainFromDto(dto.getQuestion()));
        return domain;
    }

    public List<Options> populateDomainFromDto(final List<OptionDto> dtos) {
        List<Options> domains = new ArrayList<Options>();
        for(OptionDto dto : dtos) {
            domains.add(this.populateDomainFromDto(dto));
        }
        return domains;
>>>>>>> refs/remotes/origin/master
    }

}

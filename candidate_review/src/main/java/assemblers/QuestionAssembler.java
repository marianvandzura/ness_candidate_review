package assemblers;

import dao.ICategoriesDao;
import dto.QuestionDto;
import model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public QuestionDto extractDtoFromDomain(final Questions domain) {
        QuestionDto dto = new QuestionDto();
        dto.setId(domain.getQuestionId());
        dto.setCode(domain.getCode());
        dto.setImageUrl(domain.getImageUrl());
        dto.setLanguage(domain.getLanguage());
        dto.setLevel(domain.getLevel());
        dto.setQuestion(domain.getQuestion());
        dto.setType(domain.getType());
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
<<<<<<< HEAD
        List<Categories> catDomains = new ArrayList<Categories>();
        //TODO ?
        for (CategoryDto categoryDto : dto.getCategories()) {
            catDomains.add(categoriesDao.findById(1));
        }
        domain.setCategories(catDomains);
=======
        domain.setCategory(categoriesDao.findById(dto.getCategory().getId()));
>>>>>>> refs/remotes/origin/master
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

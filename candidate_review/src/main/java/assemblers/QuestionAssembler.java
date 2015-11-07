package assemblers;

import dao.ICategoriesDao;
import dto.CategoryDto;
import dto.QuestionDto;
import model.Categories;
import model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for assembling Question objects to DTO(Data Transprt Objects) from domain and vice versa.
 * <p>
 * Created by Peter.
 */

@Component
public class QuestionAssembler {

    @Autowired
    ICategoriesDao categoriesDao;

    @Autowired
    CategoryAssembler categoryAssembler;

    public QuestionAssembler() {
        //default
    }

    /**
     * create QuestionDto object from domain model Questions
     *
     * @param domain Questions model
     * @return QuestionDto object
     */
    public QuestionDto extractDtoFromDomain(final Questions domain) {
        QuestionDto dto = new QuestionDto();
        dto.setId(domain.getQuestionId());
        dto.setCode(domain.getCode());
        dto.setImageUrl(domain.getImageUrl());
        dto.setLanguage(domain.getLanguage());
        dto.setLevel(domain.getLevel());
        dto.setQuestion(domain.getQuestion());
        dto.setType(domain.getType());
        CategoryDto category = categoryAssembler.extractDtoFromDomain(domain.getCategory());
        dto.setCategory(category);
        dto.setCategoryId(category.getId());
        return dto;
    }

    /**
     * create domain object from DTO
     *
     * @param dto
     * @return domain object
     */
    public Questions populateDomainFromDto(final QuestionDto dto) {
        Questions domain = new Questions();
        domain.setCode(dto.getCode());
        domain.setImageUrl(dto.getImageUrl());
        domain.setLanguage(dto.getLanguage());
        domain.setLevel(dto.getLevel());
        domain.setQuestion(dto.getQuestion());
        domain.setType(dto.getType());
        int categoryId = dto.getCategoryId();
        Categories category = categoriesDao.findById(categoryId);
        domain.setCategory(category);
        return domain;
    }

    /**
     * Extract List of DTOs from domain.
     *
     * @param domain
     * @return extracted DTOs
     */
    public List<QuestionDto> extractDtoListFromDomain(final List<Questions> domain) {
        List<QuestionDto> questionDtoArrayList = new ArrayList<QuestionDto>();
        for (Questions question : domain) {
            questionDtoArrayList.add(extractDtoFromDomain(question));
        }
        return questionDtoArrayList;
    }

    /**
     * create domain objects from DTO List
     *
     * @param dtos
     * @return List of domain objects
     */
    public List<Questions> populateDomainFromDto(final List<QuestionDto> dtos) {
        //init list size because of performance
        List<Questions> domains = new ArrayList<Questions>(dtos.size());
        for (QuestionDto dto : dtos) {
            domains.add(this.populateDomainFromDto(dto));
        }
        return domains;
    }
}

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
        List<CategoryDto> catDtos = new ArrayList<CategoryDto>();
        for (Categories category : domain.getCategories()) {
            catDtos.add(categoryAssembler.extractDtoFromDomain(category));
        }
        dto.setCategories(catDtos);
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
        List<Categories> catDomains = new ArrayList<Categories>();
        for (CategoryDto categoryDto : dto.getCategories()) {
            catDomains.add(categoriesDao.findById(1));
        }
        domain.setCategories(catDomains);
        return domain;
    }
}

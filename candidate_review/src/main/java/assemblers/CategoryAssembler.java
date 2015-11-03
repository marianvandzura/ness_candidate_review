package assemblers;

import dto.CategoryDto;
import dto.QuestionDto;
import model.Categories;
import model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for assembling Category objects to DTO(Data Transprt Objects) from domain.
 * <p>
 * Created by Peter.
 */
@Component
public class CategoryAssembler {

    @Autowired
    QuestionAssembler questionAssembler;

    /**
     * Extract DTO from domain.
     * CategoryDTO does not need to be connected to question.
     *
     * @param domain
     * @return extracted DTO
     */
    public CategoryDto extractDtoFromDomain(final Categories domain) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(domain.getCategoryId());
        categoryDto.setCategoryName(domain.getCategoryName());
        return categoryDto;
    }

    /**
     * create domain object from DTO
     * @param dto
     * @return domain object
     */
    public Categories populateDomainFromDto(final CategoryDto dto) {
        Categories domain = new Categories();
        domain.setCategoryId(dto.getId());
        domain.setCategoryName(dto.getCategoryName());

        List<Questions> questionDomains = new ArrayList<Questions>();
        for (QuestionDto questionDto : dto.getQuestions()) {
            questionDomains.add(questionAssembler.populateDomainFromDto(questionDto));
        }
        domain.setQuestions(questionDomains);
        return domain;
    }

    /**
     * Extract List of DTOs from domain.
     *
     * @param domain
     * @return extracted DTOs
     */
    public List<CategoryDto> extractDtoFromDomain(final List<Categories> domain) {
        List<CategoryDto> categoryDtoArrayList = new ArrayList<CategoryDto>();
        for (Categories category : domain) {
            categoryDtoArrayList.add(extractDtoFromDomain(category));
        }
        return categoryDtoArrayList;
    }
}

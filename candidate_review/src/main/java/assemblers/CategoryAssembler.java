package assemblers;

import dto.CategoryDto;
import dto.QuestionDto;
import model.Categories;
import model.Questions;
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

    public Categories populateDomainFromDto(final CategoryDto dto) {
        Categories domain = new Categories();
        domain.setCategoryId(dto.getId());
        domain.setCategoryName(dto.getCategoryName());

//        List<Questions> questionDomains = new ArrayList<Questions>();
//        //TODO ?
//        for (QuestionDto questionDto : dto.get()) {
//            catDomains.add(categoriesDao.findById(1));
//        }
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

    /**
     * create domain object from DTO
     * @param dto
     * @return domain object
     */
    public Categories populateDomainFromDto(final CategoryDto dto) {
        Categories domain = new Categories();
        domain.setCategoryName(dto.getCategoryName());
        return domain;
    }

    /**
     * create domain objects from DTO List
     * @param dtos
     * @return List of added domain objects
     */
    public List<Categories> populateDomainFromDto(List<CategoryDto> dtos) {
        //init list size because of performance
        List<Categories> domains = new ArrayList<Categories>(dtos.size());
        for (CategoryDto dto : dtos) {
            domains.add(this.populateDomainFromDto(dto));
        }
        return domains;
    }
}

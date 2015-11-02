package assemblers;

import dto.CategoryDto;
import model.Categories;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class for assembling Category objects to DTO(Data Transprt Objects) from domain.
 *
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

package service;

import assemblers.CategoryAssembler;
import dao.ICategoriesDao;
import dao.impl.CategoriesDao;
import dto.CategoryDto;
import model.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Category service
 *
 * Created by Peter.
 */
@Service
public class CategoryService {

    @Autowired
    ICategoriesDao categoriesDao;

    @Autowired
    CategoryAssembler categoryAssembler;

    public Categories addCategory(final CategoryDto categoryDto) {
        return categoriesDao.addCategory(categoryAssembler.populateDomainFromDto(categoryDto));
    }

    public List<CategoryDto> findCategoriesByQuestion(final Integer questId) {
        return categoryAssembler.extractDtoFromDomain(categoriesDao.findCategoriesByQuestion(questId));
    }


}

package service;

import assemblers.CategoryAssembler;
import dao.ICategoriesDao;
import dto.CategoryDto;
import model.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Category service
 *
 * Created by Marian_Vandzura on 7.11.2015.
 */
@Service
public class CategoryService {

    @Autowired
    ICategoriesDao categoriesDao;

    @Autowired
    CategoryAssembler categoryAssembler;

    public CategoryService(){
        //default
    }

    /**
     * get category with ID
     * @param id
     * @return CategoryDto object
     */
    public CategoryDto getCategoryById(int id) {
        return categoryAssembler.extractDtoFromDomain(categoriesDao.findById(id));
    }

    /**
     * add category to DB
     * @param categoryDto
     * @return added QuestionDto object
     */
    public CategoryDto addCategory(CategoryDto categoryDto) {
        return categoryAssembler.extractDtoFromDomain(categoriesDao.addCategory(categoryAssembler.populateDomainFromDto(categoryDto)));
    }

    /**
     * update category
     * @param categoryDto
     * @return updatedCategory
     */
    public CategoryDto updateCategory(CategoryDto categoryDto){
        Categories category = categoryAssembler.populateDomainFromDto(categoryDto);
        category.setCategoryId(categoryDto.getId());
        return categoryAssembler.extractDtoFromDomain(categoriesDao.addCategory(category));
    }

    /**
     * delete category
     * @param categoryDto
     */
    public void deleteCategory(CategoryDto categoryDto){
        categoriesDao.deleteCategory(categoryDto.getId());
    }

    /**
     * get all categories
     * @return List of categories
     */
    public List<CategoryDto> getAllCategories() {
        return categoryAssembler.extractDtoFromDomain(categoriesDao.getAllCategories());
    }
}

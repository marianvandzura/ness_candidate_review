package controllers;

import assemblers.CategoryAssembler;
import dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.CategoryService;

import java.util.List;

/**
 * Class to handle REST requests
 * <p>
 * Created by Marian_Vandzura on 7.11.2015.
 */
@Controller
public class CategoryController {

    @Autowired
    CategoryAssembler categoryAssembler;

    @Autowired
    CategoryService categoryService;

    public CategoryController() {
        //default
    }

    /**
     * get all categories
     *
     * @return List of categories
     */
    @RequestMapping(value = "/categories/", method = RequestMethod.GET)
    @ResponseBody
    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> result = categoryService.getAllCategories();
        return result;
    }

    /**
     * save category
     *
     * @param category
     * @return HTTP response
     */
    @RequestMapping(value = "/category/", method = RequestMethod.POST)
    public ResponseEntity saveCategory(@RequestBody CategoryDto category) {
        //add category
        CategoryDto savedCategory = categoryService.addCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }

    /**
     * update category
     *
     * @param category
     * @return HTTP response
     */
    @RequestMapping(value = "/category/", method = RequestMethod.PUT)
    public ResponseEntity updateCategory(@RequestBody CategoryDto category) {
        //update category
        CategoryDto updatedCategory = categoryService.updateCategory(category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    /**
     * delete category with passed id
     *
     * @param categoryId
     * @return HTTP response
     */
    @RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCategory(@PathVariable(value = "id") int categoryId) {
        //get existing category
        CategoryDto categoryToDelete = categoryService.getCategoryById(categoryId);
        if (categoryToDelete != null) {
            categoryService.deleteCategory(categoryToDelete);
            return new ResponseEntity("Category deleted", HttpStatus.OK);
        }else {
            return new ResponseEntity("Category NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }
}

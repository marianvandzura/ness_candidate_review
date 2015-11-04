package controllers;

import dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.CategoryService;

/**
 * Created by Lubomir on 11/4/2015.
 */

@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/{txt}/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    CategoryDto saveCategory(@PathVariable(value = "txt") String txt, @PathVariable(value = "id") Integer id) {
        CategoryDto catDto = new CategoryDto();
        catDto.setCategoryName(txt);
        catDto.setId(id);
        return null;
    }
}
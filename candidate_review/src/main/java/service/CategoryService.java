package service;

import assemblers.CategoryAssembler;
import dao.ICategoriesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



}

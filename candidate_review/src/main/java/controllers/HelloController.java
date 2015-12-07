package controllers;

import dao.ICategoriesDao;
import dao.IPersonDao;
import dto.CategoryDto;
import dto.QuestionDto;
import dto.UserDto;
import model.UserPassword;
import model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.CategoryService;
import service.PersonService;
import service.QuestionService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {
	//auto set from context
	@Autowired
	IPersonDao personDao;

	@Autowired
	PersonService personService;

	@Autowired
	QuestionService questionService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ICategoriesDao categoriesDao;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/" , method = RequestMethod.GET)
	public @ResponseBody CategoryDto printWelcome() {
		//model.addAttribute("message", "Hello world!");
		//return name(location) of view template
		ModelAndView modelAndView = new ModelAndView("hello");
//		Person person = new Person();
//		person.setName("NESS");
//		personDao.addPerson(person);
//		List<Person> persons = personService.getAll();
//		modelAndView.addObject("persons", fromDB);

//		QuestionDto question = new QuestionDto();
//		CategoryDto category = new CategoryDto();
//		category.setId(1);
//		question.setType(20);
//		question.setQuestion("xxxx");
//		question.setLanguage("mongolsky");
//		question.setLevel(20);
//		question.setCode("akoze");
//		question.setCategories(Arrays.asList(category));
//		Questions savedQuestion = questionService.addQuestion(question);
//		QuestionDto questionDtoFromDB = questionService.getQuestionById(savedQuestion.getQuestionId());
//		List<CategoryDto> categoriesForQuestion = categoryService.findCategoriesByQuestion(questionDtoFromDB.getId());

		QuestionDto question = new QuestionDto();
		CategoryDto category = new CategoryDto();
		category.setCategoryName("Java category");
		CategoryDto savedCategory = categoryService.addCategory(category);
//		Categories category = new Categories();
//		category.setId(21);
//		category.setCategoryName("JAVA");

		question.setCategory(category);
		question.setCode("code");
		question.setLevel(2);
		question.setLanguage("SK");
		question.setQuestion("Ako byt dobry?");

		question.setType("checkbox");

		//create admin user if not exist
		String userName = "admin";
		if(userService.getUserByUserName(userName) == null) {
			UserDto user = new UserDto();
			user.setUserName("admin");
			Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			String pwd = passwordEncoder.encodePassword(userName, null);
			user.setUserPassword(pwd);
			user.setEnabled(true);
			List<String> userRolesList = new ArrayList<>(1);
			userRolesList.add(UserRole.ROLE_ADMIN);
			user.setUserRoles(userRolesList);
			user.setEmail("admin@ness.sk");
			UserDto savedUser = userService.addUser(user);
		}

		//List<QuestionDto> fromDb = questionService.getQuestionsByCategoryId(20);


//		ObjectMapper mapper = new ObjectMapper();
//		String jason = new String();
//		try {
//			jason = mapper.writeValueAsString(questionDtoFromDB);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		modelAndView.addObject("persons", category);
		return savedCategory;
	}
}
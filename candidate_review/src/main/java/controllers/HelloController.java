package controllers;

import dao.impl.PersonDao;
import model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
//@RequestMapping("/") //urls accepted e.x /index -> define accepted extensions in web.xml
public class HelloController {
	//auto set from context
	@Autowired
	PersonDao personDao;

	@RequestMapping(value = "/" , method = RequestMethod.GET)
	public ModelAndView printWelcome() {
		//model.addAttribute("message", "Hello world!");
		//return name(location) of view template
		ModelAndView modelAndView = new ModelAndView("hello");
		Person person = new Person();
		person.setName("NESS");
		personDao.addPerson(person);


		List<Person> persons = personDao.getAllPersons();
        modelAndView.addObject("persons", persons);
		return modelAndView;
	}
}
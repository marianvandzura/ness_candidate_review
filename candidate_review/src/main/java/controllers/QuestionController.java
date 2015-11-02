package controllers;

import model.Questions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Peter on 31.10.2015.
 */

@Controller
public class QuestionController {

    @RequestMapping(value = "/{id}/qest", method = RequestMethod.GET)
    public String getQuestion(@PathVariable(value = "id") String id) {
        Questions question = new Questions();

        return "index";
    }
}

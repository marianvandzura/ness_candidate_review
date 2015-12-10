package controllers;

import dto.UserDto;
import model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public
    @ResponseBody
    void printWelcome() {
        //create admin user if not exist
        String userName = "admin";
        if (userService.getUserByUserName(userName) == null) {
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
    }
}
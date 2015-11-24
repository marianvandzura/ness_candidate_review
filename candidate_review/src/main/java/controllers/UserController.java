package controllers;

import dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.List;

/**
 * Created by Marian_Vandzura on 21.11.2015.
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    public UserController() {
        //default
    }

    /**
     * get user info - pwd not included
     *
     * @param userName
     * @return user info
     */
    @RequestMapping(value = "/admin/user/info/{user_name}", method = RequestMethod.GET)
    @ResponseBody
    public UserDto getUserInfo(@PathVariable(value = "user_name") String userName) {
        UserDto userDto = userService.getUserByUserName(userName);
        //do not send password
        if (userDto != null) {
            userDto.setUserPassword(null);
        }
        return userDto;
    }

    /**
     * get user
     *
     * @param userName
     * @return UserDto object
     */
    @RequestMapping(value = "/admin/user/{user_name}", method = RequestMethod.GET)
    @ResponseBody
    public UserDto getUser(@PathVariable(value = "user_name") String userName) {
        UserDto userDto = userService.getUserByUserName(userName);
        return userDto;
    }

    /**
     * get all user
     *
     * @return list of UserDto objects
     */
    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    @ResponseBody
    public List<UserDto> getAllUser() {
        List<UserDto> userDto = userService.getAllUsers();
        return userDto;
    }

    /**
     * save user
     *
     * @param user
     * @return saved UserDto object
     */
    @RequestMapping(value = "/admin/user/", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody final UserDto user) {
        UserDto savedUser = userService.addUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    /**
     * update user
     *
     * @param user
     * @return HTTP response
     */
    @RequestMapping(value = "/admin/user/", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody UserDto user) {
        //update question
        UserDto updatedUser = userService.addUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * delete user
     *
     * @param userId
     * @return HTTP response
     */
    @RequestMapping(value = "/admin/user/{user_id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable(value = "user_id") int userId) {
        //get existing question
        UserDto userToDelete = userService.getUserById(userId);
        if (userToDelete != null) {
            //delete question
            userService.deleteUser(userToDelete);
            return new ResponseEntity("User deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity("User NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }
}

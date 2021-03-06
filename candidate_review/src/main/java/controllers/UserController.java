package controllers;

import dto.UserDto;
import model.UserRole;
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
//        //to prevent endless json
//        List<UserRole> userRoles = userDto.getUserRoles();
//        for(UserRole singleRole : userRoles){
//            singleRole.setUsers(null);
//        }
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
//        //to prevent endless json
//        userDto.getUserPassword().setUser(null);
//        List<UserRole> userRoles = userDto.getUserRoles();
//        for(UserRole singleRole : userRoles){
//            singleRole.setUsers(null);
//        }
        return userDto;
    }

    /**
     * get user by id
     *
     * @param userId
     * @return UserDto object
     */
    @RequestMapping(value = "/admin/user/id/{user_id}", method = RequestMethod.GET)
    @ResponseBody
    public UserDto getUser(@PathVariable(value = "user_id") int userId) {
        UserDto userDto = userService.getUserById(userId);
//        //to prevent endless json
//        userDto.getUserPassword().setUser(null);
//        List<UserRole> userRoles = userDto.getUserRoles();
//        for(UserRole singleRole : userRoles){
//            singleRole.setUsers(null);
//        }
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
        List<UserDto> userList = userService.getAllUsers();
//        //to prevent endless json
//        for(UserDto user: userList){
//            user.getUserPassword().setUser(null);
//            List<UserRole> userRoles = user.getUserRoles();
//            for(UserRole singleRole : userRoles){
//                singleRole.setUsers(null);
//            }
//        }
        return userList;
    }

    /**
     * save user
     *
     * @param user
     * @return saved UserDto object
     */
    @RequestMapping(value = "/admin/user", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody final UserDto user) {
        //check if user with user_name already exists
        if (userService.getUserByUserName(user.getUserName()) != null) {
            //user already exists
            return new ResponseEntity<>("User with username: " + user.getUserName() + " already exists", HttpStatus.CONFLICT);
        }
        //check if username is passed
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            //username cannot be null
            return new ResponseEntity<>("Username cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        UserDto savedUser = userService.addUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    /**
     * update user
     *
     * @param user
     * @return HTTP response
     */
    @RequestMapping(value = "/admin/user", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody UserDto user) {
        UserDto userToUpdateDto = userService.getUserById(user.getUserId());
        if (userToUpdateDto == null) {
            //user not found
            return new ResponseEntity<>("User with ID: " + user.getUserId() + " not found", HttpStatus.NOT_FOUND);
        }
        String nameInDb = userToUpdateDto.getUserName();
        String newName = user.getUserName();
        if (!nameInDb.equals(newName)) {
            //username cannot be updated
            return new ResponseEntity<>("Username cannot be changed", HttpStatus.BAD_REQUEST);
        }

        userToUpdateDto = userService.updateUserDto(userToUpdateDto, user);
        //update question
        UserDto updatedUser = userService.updateUser(userToUpdateDto);
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
            return new ResponseEntity("[]", HttpStatus.OK);
        } else {
            return new ResponseEntity("[]", HttpStatus.NOT_FOUND);
        }
    }
}

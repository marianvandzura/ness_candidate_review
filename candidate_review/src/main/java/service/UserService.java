package service;

import assemblers.UserAssembler;
import dao.IUserDao;
import dto.UserDto;
import model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Marian_Vandzura on 14.11.2015.
 */

@Service
public class UserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private UserAssembler userAssembler;

    /**
     * get user by user_name
     *
     * @param userName
     * @return UserDto object
     */
    public UserDto getUserByUserName(String userName) {
        model.User user;
        if ((user = userDao.findUserByUserName(userName)) != null) {
            return userAssembler.extractDtoFromDomain(user);
        }
        return null;
    }

    /**
     * get user by user_id
     *
     * @param userId
     * @return UserDto object
     */
    public UserDto getUserById(int userId) {
        model.User user;
        if ((user = userDao.findUserById(userId)) != null) {
            return userAssembler.extractDtoFromDomain(user);
        }
        return null;
    }

    public List<UserDto> getAllUsers(){
        return userAssembler.extractDtoFromDomain(userDao.getAllUsers());
    }

    /**
     * add user to DB
     *
     * @param userDto
     * @return added UserDto object
     */
    public UserDto addUser(final UserDto userDto) {
        return userAssembler.extractDtoFromDomain(userDao.addUser(userAssembler.populateDomainFromDto(userDto)));
    }

    /**
     * update user
     *
     * @param userDto
     * @return updated UserDto object
     */
    public UserDto updateUser(final UserDto userDto) {
        return userAssembler.extractDtoFromDomain(userDao.addUser(userAssembler.populateDomainFromDto(userDto)));
    }

    /**
     * delete user
     *
     * @param userDto
     */
    public void deleteUser(final UserDto userDto) {
        userDao.deleteUser(userDto.getUserId());
    }
}

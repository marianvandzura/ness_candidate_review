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
public class UserService implements UserDetailsService {

    //get user from the database, via Hibernate
    @Autowired
    private IUserDao userDao;

    @Autowired
    private UserAssembler userAssembler;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {

        model.User user = userDao.findUserByUserName(userName);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRoles());

        return buildUserForAuthentication(user, authorities);
    }

    // Converts model.User user to
    // org.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(model.User user, List<GrantedAuthority> authorities) {
        return new User(user.getUserName(), user.getUserPassword().getPassword(), user.isEnabled(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Collection<UserRole> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        for (UserRole userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }

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
        userDao.deleteUser(userDto.getUserName());
    }
}

package assemblers;

import dao.IUserDao;
import dto.UserDto;
import model.User;
import model.UserPassword;
import model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserRoleService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Marian_Vandzura on 15.11.2015.
 */
@Component
public class UserAssembler {

    @Autowired
    IUserDao userDao;

    @Autowired
    UserRoleService userRoleService;

    public UserAssembler() {
        //default
    }

    /**
     * create UserDto object from domain model User
     *
     * @param domain User model
     * @return UserDto object
     */
    public UserDto extractDtoFromDomain(final User domain) {
        UserDto dto = new UserDto();
        dto.setUserId(domain.getUserId());
        dto.setUserName(domain.getUserName());
        dto.setUserPassword(domain.getUserPassword().getPassword());
        dto.setEmail(domain.getEmail());
        dto.setEnabled(domain.isEnabled());
        List<String> roles = new ArrayList<>(1);
        Collection<UserRole> userRoles = domain.getUserRoles();
        for (UserRole role : userRoles) {
            roles.add(role.getRole());
        }
        dto.setUserRoles(roles);
        return dto;
    }

    /**
     * Extract List of DTOs from domain.
     *
     * @param domain
     * @return extracted DTOs
     */
    public List<UserDto> extractDtoFromDomain(final Collection<User> domain) {
        List<UserDto> usersDtoArrayList = new ArrayList<>();
        for (User user : domain) {
            usersDtoArrayList.add(extractDtoFromDomain(user));
        }
        return usersDtoArrayList;
    }

    /**
     * create domain object from DTO
     *
     * @param dto
     * @return domain object
     */
    public User populateDomainFromDto(final UserDto dto) {
        User domain = new User();
        domain.setUserName(dto.getUserName());
        UserPassword userPassword = new UserPassword();
        userPassword.setPassword(dto.getUserPassword());
        domain.setUserPassword(userPassword);
        domain.setEmail(dto.getEmail());
        domain.setEnabled(dto.isEnabled());
        List<String> roles = dto.getUserRoles();
        List<UserRole> userRoles = new ArrayList<>(roles.size());
        for (String singleRole : roles) {
            UserRole userRole = new UserRole();
            userRole.setRole(singleRole);
            userRoles.add(userRole);
        }
        domain.setUserRoles(userRoles);
        return domain;
    }

    /**
     * update userDto with new values
     *
     * @param user
     * @param newUser
     * @return updated user
     */
    public UserDto updateDto(final UserDto user, final UserDto newUser) {
        user.setUserId(user.getUserId());
        user.setUserName(user.getUserName());
        user.setUserPassword(newUser.getUserPassword());
        user.setEmail(newUser.getEmail());
        user.setEnabled(newUser.isEnabled());
        user.setUserRoles(newUser.getUserRoles());
        return user;
    }
}

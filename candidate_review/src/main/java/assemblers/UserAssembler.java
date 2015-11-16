package assemblers;

import dao.IUserDao;
import dto.QuestionDto;
import dto.UserDto;
import model.Categories;
import model.Questions;
import model.User;
import model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserRoleService;
import service.UserService;

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
        dto.setUserName(domain.getUserName());
        dto.setPassword(domain.getPassword());
        dto.setEmail(domain.getEmail());
        dto.setEnabled(domain.isEnabled());
        List<String> roles = new ArrayList<>(1);
        Collection<UserRole> userRoles = domain.getUserRole();
        for (UserRole role : userRoles) {
            roles.add(role.getRole());
        }
        dto.setUserRoles(roles);
        return dto;
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
        domain.setPassword(dto.getPassword());
        domain.setEmail(dto.getEmail());
        domain.setEnabled(dto.isEnabled());
        List<UserRole> userRoles = new ArrayList<>(1);
        Collection<String> roles = dto.getUserRoles();
        for(String roleName: roles){
            UserRole role = userRoleService.getUserRoleByName(roleName);
            userRoles.add(role);
        }
        domain.setUserRole(userRoles);
        return domain;
    }
}

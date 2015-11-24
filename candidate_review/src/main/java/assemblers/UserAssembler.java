package assemblers;

import dao.IUserDao;
import dto.UserDto;
import model.User;
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
        dto.setUserName(domain.getUserName());
        dto.setUserPassword(domain.getUserPassword());
        dto.setEmail(domain.getEmail());
        dto.setEnabled(domain.isEnabled());
        List<UserRole> roles = new ArrayList<>(1);
        Collection<UserRole> userRoles = domain.getUserRoles();
        for (UserRole role : userRoles) {
            roles.add(role);
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
        domain.setUserPassword(dto.getUserPassword());
        domain.setEmail(dto.getEmail());
        domain.setEnabled(dto.isEnabled());
        List<UserRole> roles = dto.getUserRoles();
        domain.setUserRoles(roles);
        return domain;
    }
}

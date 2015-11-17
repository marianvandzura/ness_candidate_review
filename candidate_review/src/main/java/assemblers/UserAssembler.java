package assemblers;

import dto.UserDto;
import model.Users;
import org.springframework.stereotype.Component;

/**
 * Created by Lubomir on 11/3/2015.
 */
@Component
public class UserAssembler {

    public Users populateDtoFromDomain(final UserDto userDto){
        Users domain = new Users();
        domain.setUserId(userDto.getUserid());
        domain.setAccount(userDto.getAccount());
        domain.setEmail(userDto.getEmail());
        domain.setPassword(userDto.getPassword());
        domain.setPermissions(userDto.getPermissions());
        return domain;
    }

    public UserDto extractDtoFromDomain(final Users domain){
        UserDto uDto = new UserDto();
        uDto.setPermissions(domain.getPermissions());
        uDto.setPassword(domain.getPassword());
        uDto.setEmail(domain.getEmail());
        uDto.setAccount(domain.getAccount());
        uDto.setUserid(domain.getUserId());
        return uDto;
    }
}

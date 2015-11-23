package service;

import dao.IUserDao;
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
 * Created by Marian_Vandzura on 22.11.2015.
 */
@Transactional(readOnly = true)
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //get user from the database, via Hibernate
    @Autowired
    private IUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
        model.User user = userDao.findUserByUserName(userName);
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username: " + userName + " not found");
        }
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
        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);

        return result;
    }
}

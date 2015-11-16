package service;

import dao.IUserRoleDao;
import model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Marian_Vandzura on 15.11.2015.
 */
@Service
public class UserRoleService {

    @Autowired
    IUserRoleDao userRoleDao;

    public UserRole getUserRoleByName(String role){
        return userRoleDao.findUserRoleByName(role);
    }
}

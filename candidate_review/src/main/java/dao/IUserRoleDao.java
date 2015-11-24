package dao;

import model.UserRole;

/**
 * Created by Marian_Vandzura on 15.11.2015.
 */
public interface IUserRoleDao {

    /**
     * get UserRole by name
     * @param role
     * @return
     */
    UserRole findUserRoleByName(String role);
}

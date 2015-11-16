package dao;

import model.UserRole;

/**
 * Created by Marian_Vandzura on 15.11.2015.
 */
public interface IUserRoleDao {

    UserRole findUserRoleByName(String role);
}

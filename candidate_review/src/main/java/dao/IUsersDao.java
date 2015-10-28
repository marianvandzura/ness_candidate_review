package dao;

import model.Users;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IUsersDao {

    void addUsers(Users users);

    List<Users> getAllUsers();
}

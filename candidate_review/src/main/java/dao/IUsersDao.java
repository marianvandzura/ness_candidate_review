package dao;

import model.Users;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IUsersDao {

    void addUser(Users user);

    List<Users> getAllUsers();

    Users findUserById(Integer userId);
}

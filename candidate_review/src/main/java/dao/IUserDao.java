package dao;

import model.User;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IUserDao {

    User addUser(User user);

    void deleteUser(String userName);
//
//    List<User> getAllUsers();

    User findUserByUserName(String userName);
}

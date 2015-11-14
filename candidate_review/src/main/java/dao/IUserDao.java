package dao;

import model.User;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IUserDao {

//    void addUser(User user);
//
//    List<User> getAllUsers();

    User findUserByEmail(String email);
}

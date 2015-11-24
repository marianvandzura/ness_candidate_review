package dao;

import model.User;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IUserDao {

    /**
     * add/update new user to db
     * @param user
     * @return added User
     */
    User addUser(User user);

    /**
     * delete User from db
     * @param userId id of user to be deleted
     */
    void deleteUser(int userId);

    /**
     * get all users from db
     * @return
     */
    List<User> getAllUsers();

    /**
     * find user with passed userName
     * @param userName
     * @return User with userName
     */
    User findUserByUserName(String userName);

    /**
     *
     * @param userId
     * @return
     */
    User findUserById(int userId);
}

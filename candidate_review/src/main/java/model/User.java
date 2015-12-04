package model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(name = "user_name", nullable = false, columnDefinition = "varchar(255) default 'user_name'")
    private String userName;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private UserPassword userPassword;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable
    private Collection<UserRole> userRoles;

    @PreRemove
    private void onUserRemoved() {
        for (UserRole role : userRoles) {
            //remove reference from role to user
            Collection<User> allUsers = role.getUsers();
            Iterator<User> iterator = allUsers.iterator();
            while (iterator.hasNext()) {
                User user = iterator.next();
                if (user.equals(this)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    public User() {
        //default
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Collection<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public UserPassword getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(UserPassword userPassword) {
        this.userPassword = userPassword;
    }
}

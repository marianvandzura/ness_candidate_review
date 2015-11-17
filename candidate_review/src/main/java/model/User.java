package model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_name", unique = true, nullable = false, length = 45)
    private String userName;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "email")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Collection<UserRole> userRole;

    public User() {
        //default
    }

    public User(String userName, String password, boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
    }

    public User(String userName, String password,
                boolean enabled, Collection<UserRole> userRole) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.userRole = userRole;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(Collection<UserRole> userRole) {
        this.userRole = userRole;
    }
}
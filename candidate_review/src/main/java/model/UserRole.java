package model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Marian_Vandzura on 14.11.2015.
 */
@Entity
@Table(name = "user_roles")
public class UserRole {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id", unique = true, nullable = false)
    private Integer userRoleId;

    @Column(name = "role", nullable = false, columnDefinition = "varchar(255) default 'ROLE_USER'")
    private String role;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "userRoles")
    public Collection<User> users;


    public UserRole() {
        //default
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}

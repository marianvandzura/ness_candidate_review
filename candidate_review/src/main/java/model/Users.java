package model;

import javax.persistence.*;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */

@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(name = "permissions")
    private Integer permissions;
    @Column(name = "account")
    private String account;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;

    public Users() {
        //default
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass() || !(obj instanceof Questions)) {
            return false;
        }
        Users user = (Users) obj;
        return (this.userId == user.getUserId())
                && (this.email != null && this.email.equals(user.getEmail()));
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + userId;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPermissions() {
        return permissions;
    }

    public void setPermissions(Integer permissions) {
        this.permissions = permissions;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package model;

import javax.persistence.*;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */

@Entity
@Table(name = "Users")
public class Users {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name="permissions")
    private Integer permissions;
    @Column(name="account")
    private String account;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;

    public Users(){
        //default
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

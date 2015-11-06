<<<<<<< HEAD
=======


>>>>>>> refs/remotes/origin/master
package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Lubomir on 11/3/2015.
 */
public class UserDto {
    @JsonProperty("userid")
    private Integer userid;

    @JsonProperty("account")
    private String account;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("permissions")
    private Integer permissions;


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

    public Integer getPermissions() {
        return permissions;
    }

    public void setPermissions(Integer permissions) {
        this.permissions = permissions;
    }

}
<<<<<<< HEAD
=======

>>>>>>> refs/remotes/origin/master

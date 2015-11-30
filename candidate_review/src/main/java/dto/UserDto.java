package dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.UserPassword;
import model.UserRole;

import java.util.List;

/**
 * Created by Lubomir on 11/3/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("password")
    private String userPassword;

    @JsonProperty("email")
    private String email;

    @JsonProperty("enabled")
    private boolean enabled;

    @JsonProperty("user_role")
    private List<String> userRoles;

    public UserDto() {
        //default
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }
}

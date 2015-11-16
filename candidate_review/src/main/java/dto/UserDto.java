

package dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.UserRole;

import java.util.Collection;
import java.util.List;

/**
 * Created by Lubomir on 11/3/2015.
 */
public class UserDto {
    @JsonProperty("user_name")
    private String userName;

    @JsonIgnore
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("enabled")
    private boolean enabled;

    @JsonProperty("user_role")
    private Collection<String> userRoles;

    public UserDto(){
        //default
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

    public Collection<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Collection<String> userRoles) {
        this.userRoles = userRoles;
    }
}


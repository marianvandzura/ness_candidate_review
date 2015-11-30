package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

/**
 * Created by Marian_Vandzura on 20.11.2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "user_password")
public class UserPassword {

    @Id
    @Column(name = "password_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer passwordId;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private User user;

    @Column(name = "password", nullable = false)
    private String password;

    public UserPassword() {
        //default
    }

    public Integer getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(Integer passwordId) {
        this.passwordId = passwordId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        //encode password
        //pwd is hashed in frontend because of rest
//        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
//        this.password = passwordEncoder.encodePassword(password, null);
        this.password = password;
    }
}

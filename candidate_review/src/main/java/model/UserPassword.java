package model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

/**
 * Created by Marian_Vandzura on 20.11.2015.
 */
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
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }
}

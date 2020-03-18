package com.danielmaia.businessmanagementsystem.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long user_id;

    @Column(unique = true)
    @NotEmpty(message = "Please enter your username")
    private String username;

    @Column
    @NotEmpty(message = "Please enter your first name")
    @Size(min = 2, max = 20)
    private String first_name;

    @Column
    @NotEmpty(message = "Please enter your last name")
    private String last_name;

    @Column(unique = true)
    @Email(message = "Please enter a valid email")
    @NotEmpty(message = "Please enter an email address")
    private String email;

    @Column
    @NotEmpty(message = "Please enter a password")
    @Size(min = 5, message = "Password length needs to be greater than 8")
    private String password;

    @Column
    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {
        this.user_id = getUser_id();
        this.username = getUsername();
        this.email = getEmail();
        this.first_name = getFirst_name();
        this.last_name = getLast_name();
        this.password = getPassword();
        this.enabled = true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getUser_id() {
        return user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFullName() {
        return getFirst_name() + " " + getLast_name();
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        //authorityList.add(new SimpleGrantedAuthority(role.getName()));
        return authorityList;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package edu.mum.linkedapp.config;

import edu.mum.linkedapp.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {
    private Long id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String dob;

    private List<GrantedAuthority> authorities;

    public MyUserDetails(User user){
        this.userName = user.getUsername();
        this.password = user.getPassword();
        this.firstName = user.getFirstname();
        this.lastName = user.getLastname();
        this.email = user.getEmail();
        this.dob = user.getDob();
//        this.authorities = Arrays.stream(user.getRole().split(","))
//                                 .map(simpleGeneratedAuthority::new)
//                                 .collect(Collectors.toList());
    }
    public MyUserDetails(){}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
}

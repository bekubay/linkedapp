package edu.mum.linkedapp.config;

import edu.mum.linkedapp.domain.Role;
import edu.mum.linkedapp.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class JPAUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean isActive;
    private Set<Role> roles;

    public JPAUserDetails(User user) {

        username = user.getUsername();
        password = user.getPassword();
        isActive = user.getActive() == 1 ? true : false;
        roles = user.getRoles();
        System.out.println("UserDetails -----");
        System.out.println(username);
        System.out.println(user.getRoles().toArray()[0]);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());

    }

    @Override
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
        return isActive;
    }
}

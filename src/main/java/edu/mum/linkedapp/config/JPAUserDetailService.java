package edu.mum.linkedapp.config;

import edu.mum.linkedapp.domain.User;
import edu.mum.linkedapp.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import edu.mum.linkedapp.repository.IUserRepository;
import java.util.Optional;

@Service
public class JPAUserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByUsername(username);
        System.out.println(user.get().getUsername());
        user.orElseThrow(() -> new UsernameNotFoundException("Not FOUND..."));
        return new JPAUserDetails(user.get());
        /*
            List<Optional<Object>> userRole = userRepository.findUserRoleByUsername(username);
        userRole.get(0).orElseThrow(() -> new UsernameNotFoundException("Not FOUND..."));
        List<Object> results = new ArrayList<>();
        userRole.get(0).ifPresent(results::add);
        System.out.println(results.get(0));
        if(results.size()>0)
            return new JPAUserDetails((User)results.get(0));
        */
    }
}

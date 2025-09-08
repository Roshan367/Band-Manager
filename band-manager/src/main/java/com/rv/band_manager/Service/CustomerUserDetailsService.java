package com.rv.band_manager.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.rv.band_manager.Model.User;
import com.rv.band_manager.Repository.UserRepository;
import java.util.*;
import java.util.stream.Collectors;

import javax.naming.NameNotFoundException;

/**
 * Custom implementation of the UserDetailsService interface for authenticating users.
 * Loads user-specific data based on email and maps user roles to Spring Security authorities.
 */
@Service
public class CustomerUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Constructs a new instance of CustomerUserDetailsService with the specified repository.
     *
     * @param userRepository the repository used for user data access
     */
    public CustomerUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Locates the user based on the email address.
     *
     * @param email the email address of the user attempting to authenticate
     * @return a UserDetails object containing the user's information and authorities
     * @throws UsernameNotFoundException if the user is not found in the database
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Fetch the User from the database using the email
        User user = userRepository.findByEmailAndNoParent(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        //Map the user's roles to a list of GrantedAuthority objects
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());

        //Return a Spring Security User object that contains the user's email, password, and authorities
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

}

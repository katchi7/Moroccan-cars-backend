package com.ensias.moroccan_cars.services;

import com.ensias.moroccan_cars.models.User;
import com.ensias.moroccan_cars.repositories.AuthoritiesRepository;
import com.ensias.moroccan_cars.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthoritiesRepository authoritiesRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authoritiesRepository = authoritiesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User CreateUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        user.setAuthority(authoritiesRepository.findById(user.getAuthority().getId()).get());
        return user;
    }

    public User findUserByEmail(String string){
        return userRepository.findUserByEmail(string);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(s);
        if(user == null) throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", s));
        return user;
    }

    public User getCurrentUser(){
        User u = null;
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o instanceof UserDetails) {
            u = userRepository.findUserByEmail(((UserDetails) o).getUsername());
        }
        return u;
    }
}

package com.ensias.moroccan_cars.services;

import com.ensias.moroccan_cars.Dto.UserDto;
import com.ensias.moroccan_cars.models.User;
import com.ensias.moroccan_cars.repositories.AuthoritiesRepository;
import com.ensias.moroccan_cars.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Log4j2
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

    public User getUserById(int id){
        User user = null;
        Optional<User> userop =  userRepository.findById(id);
        if(userop.isPresent()){
            user = userop.get();
        }
        return user;
    }

    public List<User> findUsers(User u){
        int authority = 0;
        if(u.getAuthority() != null) authority = u.getAuthority().getId();
        log.info("Authority id : " + authority);
        return userRepository.findUsersByValues(u.getFirstName(),u.getLastName(),u.getEmail(),authority);
    }
    public List<UserDto> findAllUsers(){

        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return userDtos;
    }
}

package com.task.TaskManagementApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User1 user) {
        // Ensure the password is encoded before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User1 user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return user;
    }

    public List<User1> getAllUsers() {
        return userRepository.findAll();
    }
    public User1 getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
    public Set<User1> getUsersByIds(Set<Long> ids) {
        List<User1> userList = userRepository.findAllById(ids); // uses the built-in method
        return userList.stream().collect(Collectors.toSet()); // Convert List to Set
    }
    public User1 findById(Long id) {
        return userRepository.findById(id)
                             .orElse(null); // or throw an exception if preferred
    }

}

package com.ecommerce.UserService.service;

import com.ecommerce.UserService.Dto.RequestUserDto;
import com.ecommerce.UserService.Dto.ResponseUserDto;
import com.ecommerce.UserService.Entity.User;
import com.ecommerce.UserService.exceptions.UserNotFoundException;
import com.ecommerce.UserService.repository.UserRepository;
import com.ecommerce.UserService.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtHelper jwtHelper;



    public Optional<ResponseUserDto> findByEmail(String email){
        return userRepository.findByEmail(email).map(user -> new ResponseUserDto(
                  user.getId(), user.getName(), user.getEmail(),user.getAddresses()
                ));

    }

public ResponseUserDto saveUser(RequestUserDto userDto) {
    User user = new User();
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setRole(userDto.getRole());

    User savedUser = userRepository.save(user);

    return new ResponseUserDto( savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getAddresses());
}


public List<ResponseUserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new ResponseUserDto(user.getId(), user.getName(), user.getEmail(), user.getAddresses()))
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public boolean exitsById(Long id){
        return userRepository.existsById(id);
    }
    public Long getUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return user.getId();
    }

    public ResponseUserDto getLoggedInUserDetail(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new ResponseUserDto(user.getId(), user.getName(), user.getEmail(), user.getAddresses());
        } else {
            // Handle the case where the user is not found
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }



}

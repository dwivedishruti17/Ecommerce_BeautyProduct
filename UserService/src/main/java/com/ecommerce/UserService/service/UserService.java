package com.ecommerce.UserService.service;

import com.ecommerce.UserService.Dto.RequestUserDto;
import com.ecommerce.UserService.Dto.ResponseUserDto;
import com.ecommerce.UserService.Entity.User;
import com.ecommerce.UserService.enums.Role;
import com.ecommerce.UserService.exceptions.UserNotFoundException;
import com.ecommerce.UserService.repository.UserRepository;
import com.ecommerce.UserService.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtHelper jwtHelper;



    public Optional<ResponseUserDto> findByEmail(String email){
        logger.info("Finding user by email: " + email);
        return userRepository.findByEmail(email).map(user -> new ResponseUserDto(
                  user.getId(), user.getName(), user.getEmail(),user.getAddresses() , user.getRole()
                ));

    }

public ResponseUserDto saveUser(RequestUserDto userDto) {
    logger.info("Saving user: " + userDto.getEmail());
    User user = new User();
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setRole(userDto.getRole());

    User savedUser = userRepository.save(user);

    return new ResponseUserDto( savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getAddresses(), savedUser.getRole());
}


public List<ResponseUserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new ResponseUserDto(user.getId(), user.getName(), user.getEmail(), user.getAddresses(), user.getRole()))
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
            return new ResponseUserDto(user.getId(), user.getName(), user.getEmail(), user.getAddresses(), user.getRole());
        } else {
            // Handle the case where the user is not found
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    public ResponseUserDto updateUserRole(Long id, Role role){
        Optional<User> userOptional= userRepository.findById(id);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole(role);
            User saveduser = userRepository.save(user);

            return new ResponseUserDto(saveduser.getId(), saveduser.getName(), saveduser.getEmail(), saveduser.getAddresses(), saveduser.getRole());
        }
        else{
            throw new UserNotFoundException("user not found with id:"+ id);
        }
    }
}

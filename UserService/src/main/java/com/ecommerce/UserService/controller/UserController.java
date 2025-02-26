package com.ecommerce.UserService.controller;

import com.ecommerce.UserService.Dto.RequestUserDto;
import com.ecommerce.UserService.Dto.ResponseUserDto;
import com.ecommerce.UserService.Entity.Address;
import com.ecommerce.UserService.Entity.User;
import com.ecommerce.UserService.Dto.JwtRequest;
import com.ecommerce.UserService.Dto.JwtResponse;
import com.ecommerce.UserService.enums.Role;
import com.ecommerce.UserService.security.JwtHelper;
import com.ecommerce.UserService.service.AddressService;
import com.ecommerce.UserService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtHelper helper;
    @Autowired
    private AddressService addressService;


    @PostMapping("/register")
    public ResponseEntity<?>registerUser(@Valid @RequestBody RequestUserDto user){
        if(userService.findByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");

        }
        if(user.getRole()==null){
            user.setRole(Role.CUSTOMER);
        }
        ResponseUserDto savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = helper.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token, authentication.getName()));

    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> getAllUsers() {
        List<ResponseUserDto> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("current-user")
    public String getLoggedInUser(Principal principal){
        return principal.getName();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("{email}")
    public ResponseEntity<?> getUserByEmail(@Valid @PathVariable String email){

        Optional<ResponseUserDto> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseThrow(()-> new UsernameNotFoundException("user not found with email :"+ email));

    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        if(!userService.exitsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id:"+id);
        }
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
    @PostMapping("/{userId}/address")
    public ResponseEntity<Address> addAddress(@PathVariable Long userId, @RequestBody Address address) {
        Address savedAddress = addressService.addAddress(userId, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }
    @GetMapping("/{userId}/address")
    public ResponseEntity<List<Address>> getUserAddresses(@PathVariable Long userId) {
        List<Address> addresses = addressService.getUserAddresses(userId);
        return ResponseEntity.ok(addresses);
    }
    @DeleteMapping("/{userId}/address/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long userId,
                                                @PathVariable Long addressId,
                                                @AuthenticationPrincipal UserDetails currentUser) {
        addressService.deleteAddress(userId, addressId, currentUser.getUsername());
        return ResponseEntity.ok("Address deleted successfully.");
    }



}

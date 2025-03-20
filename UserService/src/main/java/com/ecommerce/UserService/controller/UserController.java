package com.ecommerce.UserService.controller;

import com.ecommerce.UserService.Dto.*;
import com.ecommerce.UserService.Entity.Address;
import com.ecommerce.UserService.Entity.User;
import com.ecommerce.UserService.enums.Role;
import com.ecommerce.UserService.security.JwtHelper;
import com.ecommerce.UserService.service.AddressService;
import com.ecommerce.UserService.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000", "http://localhost:3002"})
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
        try {
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Long userId = userService.getUserIdByEmail(request.getEmail());
            String token = helper.generateToken(userDetails, userId);

            return ResponseEntity.ok(new JwtResponse(token, authentication.getName()));
        }
        catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

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
    @PostMapping("/address")
    public ResponseEntity<Address> addAddress(HttpServletRequest request, @RequestBody Address address) {
        String token = helper.extractToken(request);
        Long LoggedInUserId = helper.extractUserId(token);
        Address savedAddress = addressService.addAddress(LoggedInUserId, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }
    @GetMapping("/address")
    public ResponseEntity<List<Address>> getUserAddresses(HttpServletRequest request) {
        String token = helper.extractToken(request);
        Long LoggedInUserId = helper.extractUserId(token);
        List<Address> addresses = addressService.getUserAddresses(LoggedInUserId);
        return ResponseEntity.ok(addresses);
    }
    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<String> deleteAddress(HttpServletRequest request,
                                                @PathVariable Long addressId) {
        String token = helper.extractToken(request);
        Long LoggedInUserId = helper.extractUserId(token);
        addressService.deleteAddress(LoggedInUserId, addressId);
        return ResponseEntity.ok("Address deleted successfully.");
    }

    @GetMapping("/{userId}/address/{addressId}")
    public ResponseEntity<?> getUserAddressById(@PathVariable Long userId, @PathVariable Long addressId, @RequestHeader("Authorization") String token){

       Address address =  addressService.getAddressbyUserIdAndAddressId(userId, addressId);
       return ResponseEntity.ok().body(address);
    }

    @GetMapping("userdetails")
    public ResponseEntity<?> getLoggedInUserdetaila(HttpServletRequest request){
        String token = helper.extractToken(request);
        Long LoggedInUserId = helper.extractUserId(token);
        ResponseUserDto user = userService.getLoggedInUserDetail(LoggedInUserId);
        return ResponseEntity.ok().body(user);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("{userId}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable  Long userId,@RequestBody RequestUserRoleDto userRoleDto){
        ResponseUserDto user = userService.updateUserRole(userId, userRoleDto.getRole());
        return ResponseEntity.ok().body(user);
    }




}

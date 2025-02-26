//package com.ecommerce.UserService.controller;
//
//
//import com.ecommerce.UserService.Entity.Address;
//import com.ecommerce.UserService.service.AddressService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequestMapping("/address")
//@RestController
//@RequiredArgsConstructor
//public class AddressController {
//    @Autowired
//    private final AddressService addressService;
//
//    @PostMapping("/{userId}")
//    public ResponseEntity<Address> addAddress(@PathVariable Long userId, @RequestBody Address address) {
//        Address savedAddress = addressService.addAddress(userId, address);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
//    }
//
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Address>> getUserAddresses(@PathVariable Long userId) {
//        List<Address> addresses = addressService.getUserAddresses(userId);
//        return ResponseEntity.ok(addresses);
//    }
//
//    @GetMapping("/{addressId}")
//    public ResponseEntity<Address> getAddressById(@PathVariable Long addressId) {
//        Address address = addressService.getAddressById(addressId);
//        return ResponseEntity.ok(address);
//    }
//
//
//
//    @PutMapping("/{addressId}")
//    public ResponseEntity<Address> updateAddress(@PathVariable Long addressId, @RequestBody Address updatedAddress) {
//        Address address = addressService.updateAddress(addressId, updatedAddress);
//        return ResponseEntity.ok(address);
//    }
//
//
//
//
//}

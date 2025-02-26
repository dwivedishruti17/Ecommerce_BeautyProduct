package com.ecommerce.UserService.service;

import com.ecommerce.UserService.Dto.ResponseUserDto;
import com.ecommerce.UserService.Entity.Address;
import com.ecommerce.UserService.Entity.User;
import com.ecommerce.UserService.exceptions.ResourceNotFoundException;
import com.ecommerce.UserService.exceptions.UserNotFoundException;
import com.ecommerce.UserService.repository.AddressRepository;
import com.ecommerce.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    @Autowired
    private final UserService userService;


    public Address addAddress(Long userId, Address address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        address.setUser(user);
        return addressRepository.save(address);
    }


    public List<Address> getUserAddresses(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        return addressRepository.findByUserId(userId);
    }


    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + addressId));
    }

    public Address updateAddress(Long addressId, Address updatedAddress) {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + addressId));

        existingAddress.setStreet(updatedAddress.getStreet());
        existingAddress.setArea(updatedAddress.getArea());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setPincode(updatedAddress.getPincode());
        existingAddress.setPhone(updatedAddress.getPhone());

        return addressRepository.save(existingAddress);
    }


    public void deleteAddress(Long userId, Long addressId, String loggedInEmail) {
        User loggedInUser = userRepository.findByEmail(loggedInEmail).
                orElseThrow(()-> new UserNotFoundException("User not found"));

        User targetUser = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("user not found with userId"+userId));
        if(!loggedInUser.getEmail().equals(targetUser.email)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete the address");
        }

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));
        if (!address.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Address does not belong to the specified user.");
        }
        addressRepository.deleteById(addressId);
    }
}

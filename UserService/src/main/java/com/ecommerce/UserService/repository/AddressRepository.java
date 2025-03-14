package com.ecommerce.UserService.repository;

import com.ecommerce.UserService.Entity.Address;
import com.ecommerce.UserService.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserId(Long userId);

    @Query("SELECT a FROM Address a WHERE a.user.id = :userId AND a.id = :addressId")
    Address findAddressByUserIdAndAddressId(@Param("userId") Long userId, @Param("addressId") Long addressId);



}

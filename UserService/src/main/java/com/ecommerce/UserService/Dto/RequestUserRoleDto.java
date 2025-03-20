package com.ecommerce.UserService.Dto;

import com.ecommerce.UserService.enums.Role;

public class RequestUserRoleDto {
   private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

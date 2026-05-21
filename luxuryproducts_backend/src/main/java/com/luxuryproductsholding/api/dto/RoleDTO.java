package com.luxuryproductsholding.api.dto;

public class RoleDTO {
    public String role;

    public RoleDTO(String email) {
        this.role = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

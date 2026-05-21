package com.luxuryproductsholding.api.dao;

import com.luxuryproductsholding.api.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByEmail(String email);
}

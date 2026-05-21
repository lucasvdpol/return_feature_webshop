package com.luxuryproductsholding.api.dao;

import com.luxuryproductsholding.api.models.Profiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfielRepository extends JpaRepository<Profiel, Long> {
    Optional<Profiel> findById(Long id);
    Optional<Profiel> findByUserId(Long userId);

}

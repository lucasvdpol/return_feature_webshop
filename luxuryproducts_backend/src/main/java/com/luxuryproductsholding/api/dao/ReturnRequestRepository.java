package com.luxuryproductsholding.api.dao;

import com.luxuryproductsholding.api.models.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {
    List<ReturnRequest> findByStatus(String status);
    ReturnRequest findByOrderId(long orderId);

    List<ReturnRequest> findByEmail(String email);
}

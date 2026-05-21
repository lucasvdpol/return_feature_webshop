package com.luxuryproductsholding.api.controllers;

import com.luxuryproductsholding.api.dto.ReturnRequestDTO;
import com.luxuryproductsholding.api.models.ReturnRequest;
import com.luxuryproductsholding.api.services.ReturnService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/return")
public class ReturnController {
    private ReturnService returnService;

    public ReturnController(ReturnService returnService) {
        this.returnService = returnService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<String> returnProducts(@RequestBody ReturnRequestDTO returnRequestDTO){
        this.returnService.returnProducts(returnRequestDTO);
        return ResponseEntity.ok("Added return request");
    }

    @GetMapping
    public ResponseEntity<List<ReturnRequest>> getAllReturnRequests(){
        return ResponseEntity.ok(this.returnService.getAllReturnRequests());
    }

    @PostMapping("/accept/{returnRequestId}")
    public ResponseEntity<String> acceptReturnRequest(@PathVariable long returnRequestId){
        this.returnService.acceptReturnRequest(returnRequestId);
        return ResponseEntity.ok("Accepted request");
    }

    @PostMapping("/decline/{returnRequestId}")
    public ResponseEntity<String> declineReturnRequest(@PathVariable long returnRequestId){
        this.returnService.declineReturnRequest(returnRequestId);
        return ResponseEntity.ok("Declined request");
    }

    @GetMapping("/user")
    public ResponseEntity<List<ReturnRequest>> getUserReturns(){
        return ResponseEntity.ok(this.returnService.getUserReturns());
    }

    @GetMapping("/label/{returnRequestId}")
    public ResponseEntity<byte[]> getReturnLabel(@PathVariable long returnRequestId) throws IOException {
        byte[] returnLabel = this.returnService.generateSimpleReturnLabel(returnRequestId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=retourlabel.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(returnLabel);
    }
}

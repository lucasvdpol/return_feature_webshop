package com.luxuryproductsholding.api.services;

import com.luxuryproductsholding.api.config.JWTFilter;
import com.luxuryproductsholding.api.dao.*;
import com.luxuryproductsholding.api.dto.ProductOrderDTO;
import com.luxuryproductsholding.api.dto.ReturnItemDTO;
import com.luxuryproductsholding.api.dto.ReturnRequestDTO;
import com.luxuryproductsholding.api.models.*;
import jakarta.persistence.EntityNotFoundException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReturnService {
    private OrderEntityRepository orderEntityRepository;
    private CustomUserRepository customUserRepository;
    private ReturnRequestRepository returnRequestRepository;
    private ReturnItemRepository returnItemRepository;
    private JWTFilter jwtFilter;
    private ProductRepository productRepository;

    public ReturnService(OrderEntityRepository orderEntityRepository, CustomUserRepository customUserRepository, ReturnRequestRepository returnRequestRepository, ReturnItemRepository returnItemRepository, JWTFilter jwtFilter, ProductRepository productRepository) {
        this.orderEntityRepository = orderEntityRepository;
        this.customUserRepository = customUserRepository;
        this.returnRequestRepository = returnRequestRepository;
        this.returnItemRepository = returnItemRepository;
        this.jwtFilter = jwtFilter;
        this.productRepository = productRepository;
    }

    public void returnProducts(ReturnRequestDTO returnRequestDTO){
        Optional<OrderEntity> optionalOrderEntity = this.orderEntityRepository.findById(returnRequestDTO.orderId);
        String email = this.jwtFilter.getEmail();
        if (optionalOrderEntity.isPresent()) {
            OrderEntity orderEntity = optionalOrderEntity.get();
            orderEntity.setRequestedReturn(true);
            this.orderEntityRepository.save(orderEntity);
            Date now = new Date();
            ReturnRequest returnRequest = new ReturnRequest(orderEntity, "PENDING", now, email);
            this.returnRequestRepository.save(returnRequest);
            this.returnProduct(returnRequest, returnRequestDTO.items);
        }
        else {
            throw new EntityNotFoundException("Order not found");
        }
    }

    public void returnProduct(ReturnRequest returnRequest, List<ReturnItemDTO> items) {
        for (ReturnItemDTO returnItemDTO : items) {
            ReturnItem returnItem = new ReturnItem(returnRequest, returnItemDTO.productName, returnItemDTO.quantity, returnItemDTO.price, returnItemDTO.reason);
            this.returnItemRepository.save(returnItem);
        }
    }

    public List<ReturnRequest> getAllReturnRequests() {
        return this.returnRequestRepository.findByStatus("PENDING");
    }

    public void acceptReturnRequest(long returnRequestId) {
        Optional<ReturnRequest> optionalReturnRequest = this.returnRequestRepository.findById(returnRequestId);
        if (optionalReturnRequest.isPresent()) {
            ReturnRequest returnRequest = optionalReturnRequest.get();
//            List<ReturnItem> returnItems = returnRequest.getReturnItems();
//            for (ReturnItem returnItem : returnItems){
//                Product product = this.productRepository.findByProductName(returnItem.getProductName());
//                product.setProductQuantity(product.getProductQuantity() + returnItem.getQuantity());
//                this.productRepository.save(product);
//            }
            returnRequest.setStatus("ACCEPTED");
            this.returnRequestRepository.save(returnRequest);
        }
        else{
            throw new EntityNotFoundException("Return request not found");
        }
    }

    public void declineReturnRequest(long returnRequestId) {
        Optional<ReturnRequest> optionalReturnRequest = this.returnRequestRepository.findById(returnRequestId);
        if (optionalReturnRequest.isPresent()) {
            ReturnRequest returnRequest = optionalReturnRequest.get();
            returnRequest.setStatus("DECLINED");
            this.returnRequestRepository.save(returnRequest);
        }
        else{
            throw new EntityNotFoundException("Return request not found");
        }
    }


    public List<ReturnRequest> getUserReturns() {
        String email = this.jwtFilter.getEmail();
        return this.returnRequestRepository.findByEmail(email);
    }

    public byte[] generateSimpleReturnLabel(long returnRequestId) {
        try {
            ReturnRequest returnRequest = this.findReturnRequest(returnRequestId);
            List<ReturnItem> items = this.findReturnItems(returnRequest);
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Retourlabel");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(50, 720);
            contentStream.showText("Bestelnummer: " + returnRequest.getOrder().getId());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Retouradres: LuxuryProductsHolding, Retourstraat 1, 1234AB Amsterdam");
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Reden(en):");

            for (ReturnItem item : items) {
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("- " + item.getProductName() + " | " + item.getQuantity() + " | (" + item.getReason() + ")");
            }

            contentStream.endText();
            contentStream.close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            document.close();

            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("PDF genereren is mislukt", e);        }
    }


    public ReturnRequest findReturnRequest(long returnRequestId){
        Optional<ReturnRequest> optionalReturnRequest = this.returnRequestRepository.findById(returnRequestId);
        if(optionalReturnRequest.isPresent()){
            ReturnRequest returnRequest = optionalReturnRequest.get();
            return returnRequest;
        }
        return null;
    }

    public List<ReturnItem> findReturnItems(ReturnRequest returnRequest){
        return this.returnItemRepository.findByReturnRequestId(returnRequest.getId());
    }



}


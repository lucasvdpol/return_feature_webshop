package com.luxuryproductsholding.api.controllers;

import com.luxuryproductsholding.api.dao.GiftcardDAO;
import com.luxuryproductsholding.api.dto.GiftcardDTO;
import com.luxuryproductsholding.api.models.Giftcard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/giftcard")
public class GiftcardController {

    private final GiftcardDAO giftcardDAO;

    public GiftcardController(GiftcardDAO giftcardDAO) {
        this.giftcardDAO = giftcardDAO;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Giftcard>> getGiftcards() {
        return ResponseEntity.ok(this.giftcardDAO.findAllGiftcards());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/blocked")
    public ResponseEntity<List<Giftcard>> getBlockedGiftcards() {
        return ResponseEntity.ok(this.giftcardDAO.findAllBlockedGiftcards());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/notblocked")
    public ResponseEntity<List<Giftcard>> getNotBlockedGiftcards() {
        return ResponseEntity.ok(this.giftcardDAO.findAllNotBlockedGiftcards());
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Giftcard>> getGiftcardsByUserId(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(this.giftcardDAO.findAllGiftcardsByUserId(email));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Giftcard>> getGiftcardById(@PathVariable Long id) {
        return ResponseEntity.ok(this.giftcardDAO.findGiftcardById(id));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Giftcard> createGiftcard(@RequestBody GiftcardDTO giftcardDTO) {
        Giftcard giftcard = this.giftcardDAO.makeGiftcard(giftcardDTO);
        return ResponseEntity.ok(giftcard);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/date/{id}")
    public ResponseEntity<String> updateDateGiftcard(@PathVariable Long id, @RequestBody GiftcardDTO giftcardDTO) {
        this.giftcardDAO.updateGiftcardDatum(id, giftcardDTO);
        return ResponseEntity.ok("Updated giftcarddate with id " + id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/price/{id}")
    public ResponseEntity<String> updatePriceGiftcard(@PathVariable Long id, @RequestBody GiftcardDTO giftcardDTO) {
        if (!giftcardDTO.endDate.after(new Date())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Giftcard is expired");
        }
        this.giftcardDAO.updateGiftcardPrice(id, giftcardDTO);
        return ResponseEntity.ok("Updated giftcardprice with id " + id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/block/{id}")
    public ResponseEntity<String> updateBlockGiftcard(@PathVariable Long id) {
        this.giftcardDAO.blockerenGiftcard(id);
        return ResponseEntity.ok("Blocked giftcard with id " + id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/deblock/{id}")
    public ResponseEntity<String> updateDeblockGiftcard(@PathVariable Long id) {
        this.giftcardDAO.deblockerenGiftcard(id);
        return ResponseEntity.ok("Deblocked giftcard with id " + id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/email/{giftcardCode}")
    public ResponseEntity<String> updateEmailGiftcard(@PathVariable String giftcardCode, @RequestBody GiftcardDTO giftcardDTO) {
        boolean completed = this.giftcardDAO.updateEmailGiftcard(giftcardCode, giftcardDTO);

        if (completed) {
            return ResponseEntity.ok("Updated emailgiftcard with code " + giftcardCode);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email " + giftcardDTO.userEmail + " not found or giftcardcode " + giftcardCode + " not found");
        }
    }

    @PutMapping("/emailUser/{giftcardCode}")
    public ResponseEntity<String> updateEmailUserGiftcard(@PathVariable String giftcardCode, @RequestBody GiftcardDTO giftcardDTO) {

        int updateStatus = this.giftcardDAO.updateEmailUserGiftcard(giftcardCode, giftcardDTO);

        switch(updateStatus) {
            case 2:
                return ResponseEntity.ok("E-mail succesvol updated voor giftcard " + giftcardCode);
            case 1:
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Je hebt geen toestemming om deze giftcard te wijzigen (eigenaar mismatch).");
            case 0:
            default:
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Giftcard met code " + giftcardCode + " niet gevonden.");
        }
    }


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGiftcard(@PathVariable Long id) {
        this.giftcardDAO.verwijderenGiftcard(id);
        return ResponseEntity.ok("Deleted giftcard with id " + id);
    }
}

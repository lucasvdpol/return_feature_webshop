package com.luxuryproductsholding.api.dao;

import com.luxuryproductsholding.api.dto.GiftcardDTO;
import com.luxuryproductsholding.api.models.CustomUser;
import com.luxuryproductsholding.api.models.Giftcard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GiftcardDAO {
    private final GiftcardRepository giftcardRepository;
    private final CustomUserRepository customUserRepository;

    public GiftcardDAO(GiftcardRepository giftcardRepository, CustomUserRepository customUserRepository) {
        this.giftcardRepository = giftcardRepository;
        this.customUserRepository = customUserRepository;
    }

    public List<Giftcard> findAllGiftcards() {
        return this.giftcardRepository.findAll();
    }

    public List<Giftcard> findAllGiftcardsByUserId(String email) {
        return this.giftcardRepository.findByUserEmail(email);
    }

    public List<Giftcard> findAllBlockedGiftcards() {
        return this.giftcardRepository.findByBlocked(true);
    }

    public List<Giftcard> findAllNotBlockedGiftcards() {
        return this.giftcardRepository.findByBlocked(false);
    }

    public Optional<Giftcard> findGiftcardById(Long id) {
        return this.giftcardRepository.findById(id);
    }

    public Giftcard makeGiftcard(GiftcardDTO giftcardDTO) {
        Giftcard giftcard = new Giftcard(
                giftcardDTO.date,
                giftcardDTO.price,
                giftcardDTO.geschiedenis,
                giftcardDTO.blocked,
                giftcardDTO.endDate
        );
        return this.giftcardRepository.save(giftcard);
    }


    public boolean updateEmailGiftcard(String giftcardCode, GiftcardDTO giftcardDTO) {

        boolean succes = false;

        Optional<Giftcard> giftcardOptional = this.giftcardRepository.findByGiftcardCode(giftcardCode);
        Optional<CustomUser> user = this.customUserRepository.findByEmail(giftcardDTO.userEmail);

        if (giftcardOptional.isPresent() && user.isPresent()) {
            giftcardOptional.get().setUser(user.get());
            this.giftcardRepository.save(giftcardOptional.get());
            succes = true;
        }
        return succes;
    }

    public int updateEmailUserGiftcard(String giftcardCode, GiftcardDTO giftcardDTO) {

        Optional<Giftcard> giftcardOptional = this.giftcardRepository.findByGiftcardCode(giftcardCode);
        Optional<CustomUser> userOptional = this.customUserRepository.findByEmail(giftcardDTO.userEmail);

        if (!giftcardOptional.isPresent() || !userOptional.isPresent()) {
            return 0;
        }

        Giftcard giftcard = giftcardOptional.get();
        CustomUser newUser = userOptional.get();

        if (giftcard.getUser() != null && !giftcard.getUser().getEmail().equals(newUser.getEmail())) {
            return 1;
        }

        giftcard.setUser(newUser);
        this.giftcardRepository.save(giftcard);

        return 2;
    }



    public void updateGiftcardDatum(Long id, GiftcardDTO giftcardDTO) {
        Optional<Giftcard> giftcard = this.giftcardRepository.findById(id);

        if (giftcard.isPresent()) {
            giftcard.get().setEndDate(giftcardDTO.endDate);

            this.giftcardRepository.save(giftcard.get());
        }
    }

    public void updateGiftcardPrice(Long id, GiftcardDTO giftcardDTO) {
        Optional<Giftcard> giftcard = this.giftcardRepository.findById(id);

        if (giftcard.isPresent()) {
            giftcard.get().setPrice(giftcardDTO.price);
            giftcard.get().setGeschiedenis(giftcardDTO.geschiedenis);

            this.giftcardRepository.save(giftcard.get());
        }
    }

    public void blockerenGiftcard(Long id) {
        Optional<Giftcard> giftcard = this.giftcardRepository.findById(id);
        if (giftcard.isPresent()) {
            giftcard.get().setBlocked(true);
            this.giftcardRepository.save(giftcard.get());
        }
    }

    public void deblockerenGiftcard(Long id) {
        Optional<Giftcard> giftcard = this.giftcardRepository.findById(id);
        if (giftcard.isPresent()) {
            giftcard.get().setBlocked(false);
            this.giftcardRepository.save(giftcard.get());
        }
    }

    public void verwijderenGiftcard(Long id) {this.giftcardRepository.deleteById(id);}
}

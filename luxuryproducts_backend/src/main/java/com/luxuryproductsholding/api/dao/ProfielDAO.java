package com.luxuryproductsholding.api.dao;

import com.luxuryproductsholding.api.dto.ProfielDTO;
import com.luxuryproductsholding.api.models.CustomUser;
import com.luxuryproductsholding.api.models.Profiel;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProfielDAO {

    private final ProfielRepository profielRepository;
    private final CustomUserRepository customUserRepository;

    public ProfielDAO(ProfielRepository profielRepository, CustomUserRepository customUserRepository) {
        this.profielRepository = profielRepository;
        this.customUserRepository = customUserRepository;
    }

    public List<Profiel> getProfiel() {
        List<Profiel> encryptedProfielen = this.profielRepository.findAll();

        for (Profiel profiel : encryptedProfielen) {
            decryptProfielFields(profiel);
        }

        return encryptedProfielen;
    }

    public Optional<Profiel> findProfielByUserId(Long userId) {
        Optional<Profiel> profielOptional = profielRepository.findByUserId(userId);

        profielOptional.ifPresent(this::decryptProfielFields);

        return profielOptional;
    }

    private void decryptProfielFields(Profiel profiel) {
        profiel.setGeslacht(profiel.getGeslacht());
        profiel.setGeboortedatum(profiel.getGeboortedatum());
        profiel.setReligie(profiel.getReligie());
        profiel.setAfkomst(profiel.getAfkomst());
        profiel.setMedischeGegevens(profiel.getMedischeGegevens());
        profiel.setPolitiekeVoorkeur(profiel.getPolitiekeVoorkeur());
        profiel.setSeksueleOrientatie(profiel.getSeksueleOrientatie());
        profiel.setInkomensNiveauu(profiel.getInkomensNiveauu());
        profiel.setZwangerschapssituatie(profiel.getZwangerschapssituatie());
        profiel.setSituatieOmschrijving(profiel.getSituatieOmschrijving());
    }


    @Transactional
    public void makeProfiel(ProfielDTO profielDTO) {
        Optional<CustomUser> user = this.customUserRepository.findById(profielDTO.userId);
        if (user.isPresent()) {
            Profiel profiel = new Profiel(
                    profielDTO.geslacht,
                    profielDTO.geboortedatum,
                    profielDTO.religie,
                    profielDTO.afkomst,
                    profielDTO.medischeGegevens,
                    profielDTO.politiekeVoorkeur,
                    profielDTO.seksueleOrientatie,
                    profielDTO.inkomensNiveauu,
                    profielDTO.zwangerschapssituatie,
                    profielDTO.situatieOmschrijving,
                    user.get()
            );
            this.profielRepository.save(profiel);
        }
    }


    public void updatingProfiel(Long id, ProfielDTO profielDTO) {
        Optional<Profiel> profiel = this.profielRepository.findById(id);

        if (profiel.isPresent()) {
            profiel.get().setGeslacht(profielDTO.geslacht);
            profiel.get().setGeboortedatum(profielDTO.geboortedatum);
            profiel.get().setReligie(profielDTO.religie);
            profiel.get().setAfkomst(profielDTO.afkomst);
            profiel.get().setMedischeGegevens(profielDTO.medischeGegevens);
            profiel.get().setPolitiekeVoorkeur(profielDTO.politiekeVoorkeur);
            profiel.get().setSeksueleOrientatie(profielDTO.seksueleOrientatie);
            profiel.get().setInkomensNiveauu(profielDTO.inkomensNiveauu);
            profiel.get().setZwangerschapssituatie(profielDTO.zwangerschapssituatie);
            profiel.get().setSituatieOmschrijving(profielDTO.situatieOmschrijving);

            this.profielRepository.save(profiel.get());
        }
    }

    public void deleteProfiel(Long id) {
        this.profielRepository.deleteById(id);
    }
}

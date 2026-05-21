package com.luxuryproductsholding.api.models;

import jakarta.persistence.*;

@Entity
public class Profiel {
    @Id
    @GeneratedValue
    private long id;

    private String geslacht;
    private String geboortedatum;
    private String religie;
    private String afkomst;
    private String medischeGegevens;
    private String politiekeVoorkeur;
    private String seksueleOrientatie;
    private String inkomensNiveauu;
    private String zwangerschapssituatie;
    private String situatieOmschrijving;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private CustomUser user;


    public Profiel() {}

    public Profiel(String geslacht, String geboortedatum, String religie, String afkomst, String medischeGegevens, String politiekeVoorkeur, String seksueleOrientatie, String inkomensNiveauu, String zwangerschapssituatie, String situatieOmschrijving, CustomUser user) {
        this.geslacht = geslacht;
        this.geboortedatum = geboortedatum;
        this.religie = religie;
        this.afkomst = afkomst;
        this.medischeGegevens = medischeGegevens;
        this.politiekeVoorkeur = politiekeVoorkeur;
        this.seksueleOrientatie = seksueleOrientatie;
        this.inkomensNiveauu = inkomensNiveauu;
        this.zwangerschapssituatie = zwangerschapssituatie;
        this.situatieOmschrijving = situatieOmschrijving;
        this.user = user;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(String geslacht) {
        this.geslacht = geslacht;
    }

    public String getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(String geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getReligie() {
        return religie;
    }

    public void setReligie(String religie) {
        this.religie = religie;
    }

    public String getAfkomst() {
        return afkomst;
    }

    public void setAfkomst(String afkomst) {
        this.afkomst = afkomst;
    }

    public String getMedischeGegevens() {
        return medischeGegevens;
    }

    public void setMedischeGegevens(String medischeGegevens) {
        this.medischeGegevens = medischeGegevens;
    }

    public String getPolitiekeVoorkeur() {
        return politiekeVoorkeur;
    }

    public void setPolitiekeVoorkeur(String politiekeVoorkeur) {
        this.politiekeVoorkeur = politiekeVoorkeur;
    }

    public String getSeksueleOrientatie() {
        return seksueleOrientatie;
    }

    public void setSeksueleOrientatie(String seksueleOrientatie) {
        this.seksueleOrientatie = seksueleOrientatie;
    }

    public String getInkomensNiveauu() {
        return inkomensNiveauu;
    }

    public void setInkomensNiveauu(String inkomensNiveauu) {
        this.inkomensNiveauu = inkomensNiveauu;
    }

    public String getZwangerschapssituatie() {
        return zwangerschapssituatie;
    }

    public void setZwangerschapssituatie(String zwangerschapssituatie) {
        this.zwangerschapssituatie = zwangerschapssituatie;
    }

    public String getSituatieOmschrijving() {
        return situatieOmschrijving;
    }

    public void setSituatieOmschrijving(String situatieOmschrijving) {
        this.situatieOmschrijving = situatieOmschrijving;
    }
}

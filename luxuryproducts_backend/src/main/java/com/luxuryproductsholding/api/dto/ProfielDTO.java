package com.luxuryproductsholding.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ProfielDTO {

    public String geslacht;
    public String geboortedatum;
    public String religie;
    public String afkomst;
    public String medischeGegevens;
    public String politiekeVoorkeur;
    public String seksueleOrientatie;
    public String inkomensNiveauu;
    public String zwangerschapssituatie;
    public String situatieOmschrijving;

    @JsonAlias("user_id")
    public long userId;


    public ProfielDTO(String geslacht, String geboortedatum, String religie, String afkomst, String medischeGegevens, String politiekeVoorkeur, String seksueleOrientatie, String inkomensNiveauu, String zwangerschapssituatie, String situatieOmschrijving, long userId) {
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
        this.userId = userId;
    }
}

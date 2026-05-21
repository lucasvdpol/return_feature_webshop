import { Component, inject, OnInit } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-accountdata',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './accountdata.component.html',
  styleUrl: './accountdata.component.scss'
})
export class AccountdataComponent implements OnInit {
  private httpClient = inject(HttpClient);

  protected geslacht: string = ""
  protected geboorteDatum: string = ""
  protected religie: string = ""
  protected afkomst: string = ""
  protected medischegegevens: string = ""
  protected politiekevoorkeur: string = ""
  protected seksueleorientatie: string = ""
  protected inkomensniveau: string = ""
  protected zwangerschapssituatie: string = ""
  protected situatieomschrijving: string = ""

  protected heeftProfiel: boolean = false;

  ngOnInit(): void {
    this.getProfiel()
  }

  getProfiel() {
    const token = localStorage.getItem('authToken');
    if (!token) return;

    this.httpClient.get<any>('http://localhost:8080/api/profiel/me', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    }).subscribe(
        data => {
          if (data && Object.keys(data).length > 0) {
            this.heeftProfiel = true;

            this.geslacht = data.geslacht || "";
            this.geboorteDatum = data.geboortedatum || "";
            this.religie = data.religie || "";
            this.afkomst = data.afkomst || "";
            this.medischegegevens = data.medischeGegevens || "";
            this.politiekevoorkeur = data.politiekeVoorkeur || "";
            this.seksueleorientatie = data.seksueleOrientatie || "";
            this.inkomensniveau = data.inkomensNiveauu || "";
            this.zwangerschapssituatie = data.zwangerschapssituatie || "";
            this.situatieomschrijving = data.situatieOmschrijving || "";
          }
        },
        error => {
          console.error("Kon profiel niet ophalen:", error);
          this.heeftProfiel = false;
        }
    );
  }


  postProfiel() {
    const profielData = this.buildProfielData();
    const token = localStorage.getItem('authToken');

    this.httpClient.post('http://localhost:8080/api/profiel', profielData, {
      headers: {
        Authorization: `Bearer ${token}`
      }, responseType: 'text'
    }).subscribe(
        response => {
          console.log("Profiel aangemaakt:", response);
          this.getProfiel();
        },
        error => {
          console.error("Fout bij aanmaken profiel:", error);
        }
    )
  }

  putProfiel() {
    const profielData = this.buildProfielData();
    const token = localStorage.getItem('authToken');

    this.httpClient.put('http://localhost:8080/api/profiel', profielData, {
      headers: {
        Authorization: `Bearer ${token}`
      }, responseType: 'text'
    }).subscribe(
        response => {
          console.log("Profiel bijgewerkt:", response);
          this.getProfiel();
        },
        error => {
          console.error("Fout bij updaten profiel:", error);
        }
    )
  }

  private buildProfielData() {
    return {
      geslacht: this.geslacht,
      geboortedatum: this.geboorteDatum,
      religie: this.religie,
      afkomst: this.afkomst,
      medischeGegevens: this.medischegegevens,
      politiekeVoorkeur: this.politiekevoorkeur,
      seksueleOrientatie: this.seksueleorientatie,
      inkomensNiveauu: this.inkomensniveau,
      zwangerschapssituatie: this.zwangerschapssituatie,
      situatieOmschrijving: this.situatieomschrijving
    };
  }
}

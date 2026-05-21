import {Component, inject, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {TranslatePipe} from "@ngx-translate/core";
import {NavbarComponent} from "../navbar/navbar.component";
import {Giftcardentity} from "../models/giftcardentity";
import {UIGiftcard} from "../models/giftcardentitymetquantity";
import {WinkelwagenService} from "../services/winkelwagen.service";

@Component({
  selector: 'app-giftcardoverzicht',
  imports: [
    FormsModule,
    NgForOf,
    TranslatePipe,
    NavbarComponent
  ],
  templateUrl: './giftcardoverzicht.component.html',
  styleUrl: './giftcardoverzicht.component.scss'
})
export class GiftcardoverzichtComponent implements OnInit {

  private httpClient = inject(HttpClient);

  private winkelwagenService = inject(WinkelwagenService);

  public giftcardList: UIGiftcard[] = []

  aantal = 0

  ngOnInit() {
    this.getAllGiftcardsSubscribe()
  }


  getAllGiftcardsSubscribe() {
    this.getAllGiftcards().subscribe(
        response => {
          this.giftcardList = response.map(g => ({
            ...g,
            quantity: 1
          }));
        },
        error => {
          console.log(error);
        }
    );
  }

  getAllGiftcards(): Observable<Giftcardentity[]> {
    return this.httpClient.get<Giftcardentity[]>('http://localhost:8080/api/giftcardEntity');
  }

  addToCart(giftcardDescription: string, giftcardPrice: number, giftcardQuantity: number, giftcardImg: string ) {
    console.log("descrpition" + giftcardDescription + "prijs" + giftcardPrice + "aantal" + giftcardQuantity + "plaatje" + giftcardImg)
    this.winkelwagenService.addGiftcardToCart(giftcardDescription, giftcardPrice, giftcardQuantity, giftcardImg)
    this.winkelwagenService.showCart()
  }
}

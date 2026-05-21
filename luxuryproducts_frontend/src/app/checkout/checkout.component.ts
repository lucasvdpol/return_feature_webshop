import {Component, inject, OnInit} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import {CheckoutService} from '../services/checkout.service';
import {WinkelwagenService} from '../services/winkelwagen.service';
import {Router} from '@angular/router';
import {AccountService} from '../services/account.service';
import {LoginService} from '../services/login.service';
import {HttpClient} from '@angular/common/http';
import {TranslatePipe} from '@ngx-translate/core';
import {Giftcard} from "../models/giftcard";
import {OrderDataModel} from "../models/OrderDataModel";
import {NgForOf, NgIf} from "@angular/common";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";



@Component({
  selector: 'app-checkout',
  imports: [
    TranslatePipe,
    NgIf,
    NgForOf,
    ReactiveFormsModule,
    NavbarComponent
  ],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.scss'
})
export class CheckoutComponent {
  winkelwagenService = inject(WinkelwagenService);
  checkoutService = inject(CheckoutService);
  accountService = inject(AccountService);
  loginService = inject(LoginService);
  private httpClient = inject(HttpClient);
  protected userEmail = this.loginService.email;

  userGiftcards: Giftcard[] = [];
  selectedGiftcard: Giftcard | null = null;
  showGiftcardMenu: boolean = false;

  expiredGiftcards: Giftcard[] = [];
  blockedGiftcards: Giftcard[] = [];

  private apiUrlPost = 'http://localhost:8080/api/orderEntity';

  private id: number = 0;
  private code: string = '';

  public addOrder(
      order: any,
      totaalprice: number,
      email: string,
      giftcardItems: any[]
  ): void {
    const date = new Date();

    const formattedOrder = order.map((item: { name: any; quantity: any; price: any; }) => ({
      productName: item.name,
      quantity: item.quantity,
      price: item.price
    }));


    const orderData = {
      customerName: email,
      orderDate: date,
      winkelwagen: formattedOrder,
      totalPrice: totaalprice,
      address: this.address,
      postcode: this.postcode,
      city: this.city,
      country: this.country
    };

    this.httpClient.post<OrderDataModel>('http://localhost:8080/api/orderEntity', orderData)
        .subscribe({
          next: (response) => {
            const orderId = response.id;

            const giftcardPromises: Promise<Giftcard>[] = [];

            for (let item of giftcardItems) {
              for (let i = 0; i < item.quantity; i++) {
                giftcardPromises.push(this.makeGiftcard(item.price));
              }
            }

            Promise.all(giftcardPromises).then(generatedGiftcards => {
              const giftcardsPerItem = [];

              let codeIndex = 0;
              for (let item of giftcardItems) {
                for (let i = 0; i < item.quantity; i++) {
                  giftcardsPerItem.push({
                    productName: item.name,
                    code: generatedGiftcards[codeIndex].giftcardCode
                  });
                  codeIndex++;
                }
              }

              this.checkoutService.giftcardlist.push({
                orderId: orderId,
                giftcards: giftcardsPerItem
              });

              console.log("✅ Giftcards succesvol gegenereerd:", giftcardsPerItem);

              this.accountService.toggleOptions();
              this.winkelwagenService.RemoveInhoudCart();
              this.router.navigate(['/account']);
              this.accountService.viewOrdersSelected = true;
            });

          },
          error: (err) => {
            console.error("❌ Fout bij order plaatsen:", err);
          }
        });
  }




  placeOrder(): void {
    const prijsNaGiftcard = this.totalPrice;

    // Update giftcard indien geselecteerd
    if (this.selectedGiftcard) {
      const nieuwePrijs = Math.max(0, this.selectedGiftcard.price - this.winkelwagenService.getCombinedTotalPrice());

      const updateBody = {
        price: nieuwePrijs,
        geschiedenis: this.selectedGiftcard.geschiedenis + new Date().toLocaleString('nl-NL') + " Gebruikt bij betaling, oude prijs: " + this.selectedGiftcard.price + ", nieuwe prijs: " + nieuwePrijs + " ",
        endDate: this.selectedGiftcard.endDate
      };

      this.httpClient.put(`http://localhost:8080/api/giftcard/price/${this.selectedGiftcard.id}`, updateBody, { responseType: 'text' })
          .subscribe({
            next: () => {
              console.log("Giftcard geüpdatet");
              this._verwerkOrder(prijsNaGiftcard); // Plaats order na update
            },
            error: err => {
              console.error("Fout bij giftcard update:", err);
            }
          });
    } else {
      this._verwerkOrder(prijsNaGiftcard); // Geen giftcard gebruikt
    }
  }

  get totalPrice(): number {
    const combined = this.winkelwagenService.getCombinedTotalPrice();
    if (this.selectedGiftcard) {
      return Math.max(0, combined - this.selectedGiftcard.price);
    }
    return combined;
  }

  private _verwerkOrder(prijsNaGiftcard: number): void {
    this.addOrder(
        this.allCartItems,
        prijsNaGiftcard,
        this.loginService.email,
        this.giftcardItems
    );
  }




  // private apiUrl = 'http://localhost:8080/api/orderEntity/John';
  private apiUrl: string = ''

  constructor(private router: Router,) {
  }


  cartItems = this.winkelwagenService.getCartItems();
  giftcardItems = this.winkelwagenService.getGiftcardItems();


  get allCartItems() {
    return [...this.winkelwagenService.cartItems, ...this.winkelwagenService.giftcardItems];
  }


  makeGiftcard(price: number): Promise<Giftcard> {
    const startDate = new Date();
    const endDate = new Date(startDate);
    endDate.setFullYear(endDate.getFullYear() + 2);

    const payload = {
      price: price,
      date: startDate,
      endDate: endDate,
      geschiedenis: "",
      blocked: false
    };

    return new Promise((resolve, reject) => {
      this.httpClient.post<Giftcard>('http://localhost:8080/api/giftcard', payload).subscribe({
        next: (giftcard) => {
          resolve(giftcard);
        },
        error: (err) => {
          console.error("❌ Fout bij giftcard aanmaken:", err);
          reject(err);
        }
      });
    });
  }

  toggleGiftcardMenu() {
    this.showGiftcardMenu = !this.showGiftcardMenu;

    if (this.userGiftcards.length === 0) {
      this.httpClient.get<Giftcard[]>('http://localhost:8080/api/giftcard')
          .subscribe({
            next: (cards) => {
              const today = new Date();
              this.expiredGiftcards = cards.filter(card => new Date(card.endDate) < today);
              this.blockedGiftcards = cards.filter(card => card.blocked === true);
              this.userGiftcards = cards.filter(card => new Date(card.endDate) >= today && !card.blocked && card.price > 0);
            },
            error: (err) => console.error("Fout bij ophalen giftcards:", err)
          });
    }
  }


  selectGiftcard(card: Giftcard) {
    this.selectedGiftcard = card;
    this.showGiftcardMenu = false;
  }

  toGiftcard() {
    this.router.navigate(['/aanaccountvoegen']);
  }

  protected orderForm = new FormGroup({
    "email": new FormControl(this.userEmail || "", [Validators.required, Validators.email]),
    "firstname": new FormControl("", [Validators.required, Validators.minLength(1)]),
    "lastname": new FormControl("", [Validators.required, Validators.minLength(1)]),
    "address": new FormControl("", [Validators.required, Validators.minLength(1)]),
    "postcode": new FormControl("", [Validators.required, Validators.minLength(1)]),
    "city": new FormControl("", [Validators.required, Validators.minLength(1)]),
    "country": new FormControl("", [Validators.required, Validators.minLength(1)]),
  });



  get email(){
    return this.orderForm.get('email')?.value;
  }

  get firstname(){
    return this.orderForm.get('firstname')?.value;
  }

  get lastname(){
    return this.orderForm.get('lastname')?.value;
  }

  get address(){
    return this.orderForm.get('address')?.value;
  }

  get postcode(){
    return this.orderForm.get('postcode')?.value;
  }

  get city(){
    return this.orderForm.get('city')?.value;
  }

  get country(){
    return this.orderForm.get('country')?.value;
  }


}

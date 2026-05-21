import {Component, inject} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-makegiftcard',
  imports: [
    FormsModule
  ],
  templateUrl: './makegiftcard.component.html',
  styleUrl: './makegiftcard.component.scss'
})
export class MakegiftcardComponent {

  private httpClient = inject(HttpClient);

  price: number = 0

  constructor() { }



  protected makeGiftcard() {
    const date = new Date();
    const endDate = new Date(date);
    endDate.setFullYear(date.getFullYear() + 2);

    const giftcardData = {
      date: date.toISOString(),
      endDate: endDate.toISOString(),
      price: this.price,
      geschiedenis: "",
      blocked: false
    }
    this.httpClient.post('http://localhost:8080/api/giftcard', giftcardData, {responseType: 'text'})
        .subscribe(
            response => {
              console.log("Order successfully placed:", response);

            },
            error => {
              console.error("Error placing order:", error);
            })
  }
}

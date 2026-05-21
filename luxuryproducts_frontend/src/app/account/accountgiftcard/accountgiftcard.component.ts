import {Component, inject} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {DatePipe, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-accountgiftcard',
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    DatePipe
  ],
  templateUrl: './accountgiftcard.component.html',
  styleUrl: './accountgiftcard.component.scss'
})
export class AccountgiftcardComponent {

  private httpClient = inject(HttpClient);

  giftcards: any[] = [];
  filteredGiftcards: any[] = [];

  getGiftcards(afterLoad?: () => void) {
    this.httpClient.get<any[]>('http://localhost:8080/api/giftcard').subscribe(
        response => {
          this.giftcards = response;
          this.filteredGiftcards = response;
          console.log(response);

          if (afterLoad) afterLoad(); // Voer filter uit na laden
        },
        error => console.log(error)
    );
  }

  showActiveGiftcards() {
    this.getGiftcards(() => {
      this.filteredGiftcards = this.giftcards.filter(gc => gc.price > 0 && !gc.blocked);
    });
  }

  showInactiveGiftcards() {
    this.getGiftcards(() => {
      this.filteredGiftcards = this.giftcards.filter(gc => gc.price === 0 || gc.blocked);
    });
  }




  changeEmail(email: string, code: string): void {
    const giftcardDTO = { userEmail: email };
    this.httpClient.put('http://localhost:8080/api/giftcard/emailUser/' + code, giftcardDTO, { responseType: 'text' }).subscribe(
        response => {
          console.log(response);
          this.getGiftcards();
        },
        error => {
          console.error(error);
        }
    );
  }



}

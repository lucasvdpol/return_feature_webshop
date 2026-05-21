import {Component, inject, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {DatePipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {Router} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {Giftcard} from "../../../models/giftcard";

@Component({
  selector: 'app-searchgiftcard',
  imports: [
    DatePipe,
    NgForOf,
    NgClass,
    FormsModule,
    NgIf
  ],
  templateUrl: './searchgiftcard.component.html',
  styleUrl: './searchgiftcard.component.scss'
})
export class SearchgiftcardComponent implements OnInit {

  private httpClient = inject(HttpClient);

  activeFilter: 'all' | 'blocked' | 'notblocked' = 'all';

  constructor(private router: Router) {
  }

  giftcards: Giftcard[] = [];

  searchEmail: string = '';
  searchCode: string = '';
  allGiftcards: Giftcard[] = [];

  price: number = 0;
  geschiedenis: string = "";

  ngOnInit() {
    this.getAllGiftcardsSubscribe()
  }

  saveNote(giftcardId: number, note: string) {
    console.log('Opmerking voor giftcard', giftcardId, ':', note);
  }


  getAllGiftcardsSubscribe() {
    this.activeFilter = 'all';
    this.getAllGiftcards().subscribe(giftcards => {
      this.allGiftcards = giftcards.map(g => ({
        ...g,
        showEmailInput: false,
        newEmail: '',
        editing: false,
        newEndDate: this.dateToInputValue(new Date(g.endDate))
      }));
      this.applySearch()
    });
  }

  dateToInputValue(date: Date): string {
    return date.toISOString().split('T')[0];
  }



  public getAllGiftcards(): Observable<Giftcard[]> {
    const observable = this.httpClient.get<Giftcard[]>('http://localhost:8080/api/giftcard/all');

    return observable.pipe(
        tap(
            response => console.log('Response:', response),
            error => console.error('Error:', error)
        )
    );
  }

  toAdminPanel() {
    this.router.navigate(['/adminPanel']);
  }

  onlyBlocked(): Observable<Giftcard[]> {
    const observable = this.httpClient.get<Giftcard[]>('http://localhost:8080/api/giftcard/blocked');

    return observable.pipe(
        tap(
            response => console.log('Response:', response),
            error => console.error('Error:', error)
        ));
  }

  onlyBlockedSubscribe() {
    this.activeFilter = 'blocked';
    this.onlyBlocked().subscribe(giftcards => {
      this.giftcards = giftcards.map(g => ({
        ...g,
        editing: false,
        newEndDate: this.dateToInputValue(new Date(g.endDate))
      }));
    });
  }

  onlyNotBlockedSubscribe() {
    this.activeFilter = 'notblocked';
    this.onlyNotBlocked().subscribe(giftcards => {
      this.giftcards = giftcards.map(g => ({
        ...g,
        editing: false,
        newEndDate: this.dateToInputValue(new Date(g.endDate))
      }));
    });
  }


  onlyNotBlocked(): Observable<Giftcard[]> {
    const observable = this.httpClient.get<Giftcard[]>('http://localhost:8080/api/giftcard/notblocked');

    return observable.pipe(
        tap(
            response => console.log('Response:', response),
            error => console.error('Error:', error)
        ));
  }


  blockGiftcard(id: number): void {
    this.httpClient.put('http://localhost:8080/api/giftcard/block/' + id, '', {responseType: 'text'})
        .subscribe({
          next: () => {
            console.log(`Giftcard ${id} succesvol geblokkeerd.`);
            this.refreshGiftcards();
          },
          error: (err) => {
            console.error(`Fout bij blokkeren van giftcard ${id}:`, err);
          }
        });
  }


  deblockGiftcard(id: number) {
    this.httpClient.put('http://localhost:8080/api/giftcard/deblock/' + id, '', {responseType: 'text'})
        .subscribe({
          next: () => {
            console.log(`Giftcard ${id} succesvol gedeblokkeerd.`);
            this.refreshGiftcards();
          },
          error: (err) => {
            console.error(`Fout bij deblokkeren van giftcard ${id}:`, err);
          }
        });
  }

  refreshGiftcards() {
    if (this.activeFilter === 'blocked') {
      this.onlyBlockedSubscribe();
    } else if (this.activeFilter === 'notblocked') {
      this.onlyNotBlockedSubscribe();
    } else {
      this.getAllGiftcardsSubscribe();
    }
  }

  aanpassenDeblockedGeschiedenis(id: number) {
    const origineleGiftcard = this.giftcards.find(g => g.id === id);
    if (!origineleGiftcard) {
      console.error('Giftcard niet gevonden');
      return;
    }

    const now = new Date();
    const datumTijdString = now.toLocaleString('nl-NL', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: false
    });

    const nieuweGeschiedenis = origineleGiftcard.geschiedenis + datumTijdString + " Gedeblokkeerd, ";

    const giftcard = {
      price: origineleGiftcard.price,
      geschiedenis: nieuweGeschiedenis,
      endDate: origineleGiftcard.endDate,
    };

    this.httpClient.put('http://localhost:8080/api/giftcard/price/' + id, giftcard, {responseType: 'text'}).subscribe(
        response => {
          console.log('Response:', response);
          this.refreshGiftcards();
        },
        error => console.error('Error:', error)
    );
  }

  aanpassenBlockedGeschiedenis(id: number) {
    const origineleGiftcard = this.giftcards.find(g => g.id === id);
    if (!origineleGiftcard) {
      console.error('Giftcard niet gevonden');
      return;
    }

    const now = new Date();
    const datumTijdString = now.toLocaleString('nl-NL', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: false
    });

    const nieuweGeschiedenis = origineleGiftcard.geschiedenis + datumTijdString + " Geblokkeerd, ";

    const giftcard = {
      price: origineleGiftcard.price,
      geschiedenis: nieuweGeschiedenis,
      endDate: origineleGiftcard.endDate
    };

    this.httpClient.put('http://localhost:8080/api/giftcard/price/' + id, giftcard, {responseType: 'text'}).subscribe(
        response => {
          console.log('Response:', response);
          this.refreshGiftcards();
        },
        error => console.error('Error:', error)
    );
  }

  changeDate(id: number, dateString: string) {
    const origineleGiftcard = this.giftcards.find(g => g.id === id);
    if (!origineleGiftcard) {
      console.error('Giftcard niet gevonden');
      return;
    }

    const now = new Date();
    const datumTijdString = now.toLocaleString('nl-NL', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: false
    });

    const nieuweDatumString = new Date(dateString).toLocaleDateString('nl-NL', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric'
    });

    const nieuweGeschiedenis = origineleGiftcard.geschiedenis +
        `${datumTijdString} - Vervaldatum gewijzigd naar ${nieuweDatumString}, `;

    const body = {
      endDate: new Date(dateString),
    };

    const bodyGeschiedenis = {
      price: origineleGiftcard.price,
      geschiedenis: nieuweGeschiedenis,
      endDate: origineleGiftcard.endDate,
    }

    this.httpClient.put(`http://localhost:8080/api/giftcard/date/${id}`, body, {
      headers: { 'Content-Type': 'application/json' },
      responseType: 'text'
    }).subscribe(
        response => {
          console.log('Response:', response);
          this.refreshGiftcards();
        },
        error => console.error('Error:', error)
    );
    this.httpClient.put(`http://localhost:8080/api/giftcard/price/${id}`, bodyGeschiedenis, {responseType: 'text'}).subscribe(
        response => {
          console.log('Response:', response);
        },
        error => console.error('Error:', error)
    )
  }

  applySearch() {
    const email = this.searchEmail.toLowerCase().trim();
    const code = this.searchCode.toLowerCase().trim();

    this.giftcards = this.allGiftcards.filter(gc => {
      const matchesEmail = !email || (gc.user?.email?.toLowerCase().includes(email));
      const matchesCode = !code || gc.giftcardCode.toLowerCase().includes(code);
      return matchesEmail && matchesCode;
    });
  }



  changeEmail(giftcardcode: string, newEmail: string): void {
    const payload = { userEmail: newEmail };

    this.httpClient.put('http://localhost:8080/api/giftcard/email/' + giftcardcode , payload, { responseType: 'text' })
        .subscribe({
          next: res => {
            console.log('E-mail updated:', res);
            this.getAllGiftcardsSubscribe();
          },
          error: err => {
            console.error('Fout bij wijzigen e-mail:', err);
          }
        });
  }



}


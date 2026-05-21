import {Component, inject} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AanaccountService} from "./aanaccount.service";

@Component({
  selector: 'app-aanaccountvoegencomponent',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './aanaccounttoevoegen.component.html',
  styleUrl: './aanaccounttoevoegen.component.scss'
})
export class AanaccountvoegencomponentComponent {

  private httpClient = inject(HttpClient);

  private router = inject(Router);

  account = inject(AanaccountService)

  changeEmail(email: string, code: string): void {
    const giftcardDTO = { userEmail: email };
    this.httpClient.put('http://localhost:8080/api/giftcard/emailUser/' + code, giftcardDTO, { responseType: 'text' }).subscribe(
        response => {
          console.log(response);

          if (this.account.getRoute()) {
            this.router.navigate(['/']);
          } else {
            this.router.navigate(['/checkout']);
          }
        },
        error => {
          console.error(error);
        }
    );
  }


}

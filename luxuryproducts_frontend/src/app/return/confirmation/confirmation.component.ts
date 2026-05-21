import {Component, inject} from '@angular/core';
import {Router} from "@angular/router";
import {AccountService} from "../../services/account.service";

@Component({
  selector: 'app-confirmation',
  imports: [],
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.scss'
})
export class ConfirmationComponent {
  private router = inject(Router);
  private accountService = inject(AccountService);

  onClickHome(){
    this.accountService.selectReturnProduts()
    this.router.navigate(['account']);
  }
}

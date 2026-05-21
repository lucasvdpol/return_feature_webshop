import {Component, inject, OnInit} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import {FormsModule} from '@angular/forms';
import {AccountService} from '../services/account.service';
import {ChangedataComponent} from './changedata/changedata.component';
import {ViewordersComponent} from './vieworders/vieworders.component';
import {LoginService} from '../services/login.service';
import {AddproductComponent} from './admin/addproduct/addproduct.component';
import {RemoveproductComponent} from './admin/removeproduct/removeproduct.component';
import {ChangeproductComponent} from './admin/changeproduct/changeproduct.component';
import {TranslatePipe} from '@ngx-translate/core';
import {Router} from "@angular/router";
import {ReturnedproductsComponent} from "./returnedproducts/returnedproducts.component";
import {ReturnComponent} from "./admin/return/return.component";

@Component({
  selector: 'app-account',
    imports: [
        NavbarComponent,
        FormsModule,
        ChangedataComponent,
        ViewordersComponent,
        AddproductComponent,
        RemoveproductComponent,
        ChangeproductComponent,
        TranslatePipe,
        ReturnedproductsComponent,
        ReturnComponent,
    ],
  templateUrl: './account.component.html',
  styleUrl: './account.component.scss'
})
export class AccountComponent implements OnInit {
  accountService = inject(AccountService);
  loginService = inject(LoginService);
  private router = inject(Router);

  ngOnInit() {
    this.accountService.viewOrders()
  }

  toGiftcard() {
    this.router.navigate(['/accountgiftcards']);
  }
  toAccountData() {
    this.router.navigate(['/accountdata']);
  }
}

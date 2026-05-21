import {Component, EventEmitter, inject, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {LoginService} from '../services/login.service';
import {RegisterComponent} from '../register/register.component';
import {NgIf} from '@angular/common';
import {RegisterserviceService} from '../services/registerservice.service';
import {GebruikerDataModel} from '../models/gebruikerData.model';
import {CheckoutService} from '../services/checkout.service';
import {WinkelwagenService} from '../services/winkelwagen.service';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  imports: [
    FormsModule,
    RegisterComponent,
    NgIf,
    TranslatePipe
  ],
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  error: boolean = false;
  isRegisterPopupVisible = false;
  isPopupVisible = false;
  @Output() close = new EventEmitter<void>();
  loginService = inject(LoginService);
  RegisterserviceService = inject(RegisterserviceService)
  checkoutService = inject(CheckoutService);
  winkelwagenService = inject(WinkelwagenService);


  // email: string = this.loginService.email;
  // password: string = this.loginService.password;
  private gebruikersList: any;

  onLogin() {
    this.gebruikersList = this.loginService.gebruikerList;
    console.log(this.gebruikersList);
    for (const item of this.gebruikersList) {
      console.log(item);
      console.log(item.gebruikerEmail,item.gebruikerPassword);
      if (this.loginService.email === item.gebruikerEmail && this.loginService.password === item.gebruikerWachtwoord) {
        console.log('ja');
        this.loginService.onlogin()
        this.checkoutService.getOrderss()
        this.close.emit();
        if (this.loginService.email === 'admin'){
          this.loginService.admin = true;
          console.log('admin');
        }

      }
      else{
        console.log('NEE')
        this.error = true;
      }
    }
    console.log('Email:', this.loginService.email);
    console.log('Password:', this.loginService.password);
    // this.isPopupVisible = true;

  }

  closePopup() {
    this.close.emit();
  }

  click(){
    this.RegisterserviceService.isRegisterPopupVisible = true;
    this.close.emit();
  }

  isRegistered(){
    return this.RegisterserviceService.isregistered
  }

  public toggleregisterPopup() {
    this.isRegisterPopupVisible = !this.isRegisterPopupVisible;
  }

  public async onlogin2() {
    const success = await this.loginService.callLogin();
    if (success) {
      this.close.emit();
    }
    else{
      this.error = true;
    }
  }



}

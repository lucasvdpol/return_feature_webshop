import {Component, inject, OnInit} from '@angular/core';
import {WinkelwagenService} from '../services/winkelwagen.service';
import {WinkelwagenComponent} from '../winkelwagen/winkelwagen.component';
import {LoginComponent} from '../login/login.component';
import {NgIf} from '@angular/common';
import {LoginService} from '../services/login.service';
import {RegisterserviceService} from '../services/registerservice.service';
import {RegisterComponent} from '../register/register.component';
import {Router} from '@angular/router';
import {navbarService} from '../services/navbar.service';
import {TranslateService} from '@ngx-translate/core';
import {AanaccountService} from "../aanaccounttoevoegen/aanaccount.service";

@Component({
  selector: 'app-navbar',
  imports: [
    WinkelwagenComponent,
    LoginComponent,
    NgIf,
    RegisterComponent,
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})

export class NavbarComponent implements OnInit {

  protected admin = false

  navbarService = inject(navbarService)

  constructor(private router: Router,) {
  }


  winkelwagenService = inject(WinkelwagenService);
  loginService = inject(LoginService);
  registerService = inject(RegisterserviceService);
  private translateService = inject(TranslateService)

  aanaccontservice = inject(AanaccountService)

  get isCartVisible() {
    return this.winkelwagenService.isCartVisible;
  }

  showCart() {
    this.winkelwagenService.showCart()
    console.log();
  }

  hideCart() {
    this.winkelwagenService.hideCart();
  }

  public toggleLoginPopup() {
    this.navbarService.showLoginPopup = !this.navbarService.showLoginPopup;
    this.loginService.getGebruikerListData()

  }


  isLoggedin() {
    return this.loginService.isloggedin
  }

  getRegister() {
    return this.registerService.isRegisterPopupVisible;
  }

  public toggleRegisterPopup() {
    this.registerService.isRegisterPopupVisible = !this.registerService.isRegisterPopupVisible;
  }

  acount() {
    this.router.navigate(['/account']);
  }

  home() {
    this.router.navigate(['/']);
  }

  currentLang: string = 'nl';

  switchLanguage(language: string) {
    this.currentLang = language;
    this.translateService.use(language);
    localStorage.setItem('userLanguage', language);

  }

  ngOnInit() {
    const savedLang = localStorage.getItem('userLanguage');
    if (savedLang) {
      this.currentLang = savedLang;
    }
    this.getEmail()
  }

  getEmail() {
    const email = localStorage.getItem('savedEmail')
    if (email === "admin@gmail.com") {
      this.admin = true;
    }
  }

  toAddProduct() {
    this.router.navigate(['/adminPanel']);
  }

  toGiftcards() {
    this.router.navigate(['/giftcard']);
  }
  toAddGiftcard() {
    this.router.navigate(['/aanaccountvoegen']);
  }
}

import {Component, computed, inject, Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import { WinkelwagenService } from '../services/winkelwagen.service';
import {Router} from '@angular/router';
import {LoginService} from '../services/login.service';
import {navbarService} from '../services/navbar.service';
import {CheckoutService} from '../services/checkout.service';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-winkelwagen',
  imports: [CommonModule, TranslatePipe],
  templateUrl: './winkelwagen.component.html',
  styleUrls: ['./winkelwagen.component.scss'],
  standalone: true,
})
export class WinkelwagenComponent {
  winkelwagenService = inject(WinkelwagenService);
  loginService = inject(LoginService);
  navbarService = inject(navbarService)
  checkoutService = inject(CheckoutService);
  cartItems: any[] = this.winkelwagenService.cartItems;
  empryCartPopup: boolean = false;
  giftcardItems: any[] = this.winkelwagenService.giftcardItems;

  constructor(private router: Router,) {}

  totaalprice = computed(() => this.winkelwagenService.totaalprice());

  @Input() isCartVisible: boolean = false;

  // showCart() {
  //   this.winkelwagenService.showCart();
  // }
  //
  // hideCart() {
  //   this.winkelwagenService.hideCart();
  // }
  //
  // itemPlus(item: any) {
  //   item.quantity = item.quantity + 1;
  //   this.winkelwagenService.calculateTotalPrice()
  //
  // }
  //
  // itemMin(item: any){
  //   item.quantity = item.quantity - 1;
  //   if( item.quantity == 0 ){
  //     this.itemRemove(item);
  //   }
  //   this.winkelwagenService.calculateTotalPrice()
  //
  // }
  //
  // itemRemove(item: any) {
  //   // console.log(this.cartItems);
  //   const index = this.cartItems.findIndex(cartItem => cartItem.name === item.name);
  //   // console.log(index);
  //
  //   if (index !== -1) {
  //     this.cartItems.splice(index, 1);
  //     // console.log('Item removed:', item.name);
  //   }
  //   this.winkelwagenService.calculateTotalPrice()
  // }

  checkout(){
    if (this.loginService.isloggedin){
      this.router.navigate(['/checkout']);
      this.winkelwagenService.isCartVisible = false;
    }
    else{
      this.navbarService.showLoginPopup = true
      this.loginService.getGebruikerListData()
    }
  }

  EmptyCart(){
    this.empryCartPopup = true
  }

  get hasItemsInCart(): boolean {
    return this.winkelwagenService.cartItems.length > 0 || this.winkelwagenService.giftcardItems.length > 0;
  }


}

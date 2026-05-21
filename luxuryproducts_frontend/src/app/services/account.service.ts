import {inject, Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {CheckoutService} from "./checkout.service";

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  checkoutService = inject(CheckoutService);

  private router = inject(Router);

  viewOrdersSelected: boolean = false;
  changeDataSelected: boolean = false;
  addproductSelected: boolean = false;
  removeproductSelected: boolean = false;
  changeProductSelected: boolean = false;
  returnProductsSelected: boolean = false;
  viewReturnProductsSelected: boolean = false;

  viewOrders(){
    this.toggleOptions()
    this.checkoutService.getOrderss()

    this.viewOrdersSelected = true;
  }

  changeData(){
    this.toggleOptions()
    this.changeDataSelected = true;
  }

  addproduct(){
    this.toggleOptions()
    this.addproductSelected = true;
  }

  returnProducts(){
    this.toggleOptions()
    this.returnProductsSelected = true;
  }

  removeproduct(){
    this.toggleOptions()
    this.removeproductSelected = true;
  }

  changeproduct(){
    this.toggleOptions()
    this.changeProductSelected = true;
  }

  selectReturnProduts(){
    this.toggleOptions();
    this.viewReturnProductsSelected = true;
  }


  toggleOptions(){
    this.changeDataSelected = false;
    this.viewOrdersSelected = false;
    this.addproductSelected = false;
    this.removeproductSelected = false;
    this.changeProductSelected = false;
    this.returnProductsSelected = false;
    this.viewReturnProductsSelected = false;
  }

  makeGiftcard() {
    this.router.navigate(['/makegiftcard']);
  }
  filterGiftcard(){
    this.router.navigate(['/searchgiftcard']);
  }
}

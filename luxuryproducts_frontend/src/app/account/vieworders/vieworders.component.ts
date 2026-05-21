import {Component, inject} from '@angular/core';
import {TranslatePipe} from '@ngx-translate/core';
import {OrderDataModel} from "../../models/OrderDataModel";
import {Router} from "@angular/router";
import {DatePipe, NgClass, NgIf, NgForOf} from "@angular/common";
import {CheckoutService} from "../../services/checkout.service";

@Component({
  selector: 'app-vieworders',
  imports: [
    TranslatePipe,
    NgForOf,
    NgIf,
    NgClass,
    DatePipe,
  ],
  templateUrl: './vieworders.component.html',
  styleUrl: './vieworders.component.scss'
})
export class ViewordersComponent {
  checkoutService = inject(CheckoutService);
  private router = inject(Router);
  protected showError: { [orderId: number]: boolean } = {};

  returnProduct(order: OrderDataModel) {
    if(this.checkForDaysSinceOrder(order)) {
      this.router.navigate(['return/order/' + order.id]);
    }else{

    }
  }

  checkForDaysSinceOrder(order: OrderDataModel) {
    const now = new Date();
    const orderDate = new Date(order.orderDate); // Zorg dat dit echt een Date is
    const diffInMs = now.getTime() - orderDate.getTime(); // Verschil in milliseconden
    const diffInDays = Math.floor(diffInMs / (1000 * 60 * 60 * 24)); // Omrekenen naar dagen
    if (diffInDays > 30) {
      this.showError[order.id] = true;
      return false;
    }
    return true;

  }


  constructor() {
    this.checkoutService.getOrderss();
  }
}


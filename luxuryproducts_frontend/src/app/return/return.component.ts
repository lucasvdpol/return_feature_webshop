import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CheckoutService} from "../services/checkout.service";
import {OrderDataModel} from "../models/OrderDataModel";
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from '@angular/common';
import {ReturnService} from "../services/return.service";

@Component({
  selector: 'app-return',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './return.component.html',
  styleUrl: './return.component.scss'
})
export class ReturnComponent implements OnInit {
  orderId: number = 0;
  order: OrderDataModel | undefined;
  returnForm!: FormGroup;
  private returnService = inject(ReturnService);
  private router = inject(Router);

  constructor(
      private route: ActivatedRoute,
      private checkoutService: CheckoutService,
      private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.orderId = Number(this.route.snapshot.paramMap.get('orderId')!);
    this.order = this.checkoutService.orderlist.find(
        (order: OrderDataModel) => order.id === this.orderId
    );

    if (this.order) {
      const productGroups = this.order.winkelwagen.map(product =>
          this.fb.group({
            selected: [false],
            productName: [product.productName],
            quantity: [product.quantity],
            price: [product.price],
            returnQuantity: [1],
            returnReason: ['']
          })
      );

      this.returnForm = this.fb.group({
        products: this.fb.array(productGroups)
      });
    }
  }

  get productsFormArray(): FormArray {
    return this.returnForm.get('products') as FormArray;
  }

  createRange(start: number, end: number): number[] {
    return Array.from({length: (end - start) + 1}, (_, i) => start + i);
  }

  returnProducts() {
    const selectedProducts = this.productsFormArray.controls
        .filter(ctrl => ctrl.get('selected')?.value)
        .map(ctrl => ({
          productName: ctrl.get('productName')?.value,
          quantity: ctrl.get('returnQuantity')?.value,
          price: ctrl.get('price')?.value,
          reason: ctrl.get('returnReason')?.value,
        }));


    if (selectedProducts.length === 0) {
      alert("Selecteer minstens één product om te retourneren.");
      return;
    }
    for (let i = 0; i < selectedProducts.length; i++) {
      const product = selectedProducts[i];
      if(product.reason === ""){
        alert("Selecteer een reden");
        return;
      }
    }

    this.returnService.returnRequest(selectedProducts, this.orderId);
    this.router.navigate(['return/confirmation']);
  }

  shouldShowReturnCostMessage(): boolean {
    return this.productsFormArray.controls.some(control => {
      const reason = control.get('returnReason')?.value;
      return reason === 'niet nodig' || reason === 'anders';
    });
  }

  get visibleProductControls() {
    return this.productsFormArray.controls.filter(control => {
      const name = control.get('productName')?.value?.toLowerCase();
      return !name?.includes('giftcard');
    });
  }

}
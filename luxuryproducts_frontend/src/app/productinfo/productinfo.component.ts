import {Component, computed, EventEmitter, inject, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {ProductinfoService} from '../services/productinfo.service';
import {WinkelwagenService} from '../services/winkelwagen.service';
import {NgIf} from '@angular/common';
import {TranslatePipe} from '@ngx-translate/core';
import {CategoryService} from "../services/category.service";

interface Oninit {
}

@Component({
  selector: 'app-productinfo',
  imports: [
    FormsModule,
    TranslatePipe
  ],
  templateUrl: './productinfo.component.html',
  styleUrl: './productinfo.component.scss'
})
export class ProductinfoComponent implements Oninit{

  // constructor(public productService: ProductinfoService, ) {}

  private productService = inject(ProductinfoService);
  private categoryService = inject(CategoryService)

  protected shownProducts =  this.productService.getShownProducts();
  protected   categorielist = this.categoryService.getCategories()
  productservices = inject(ProductinfoService)
  winkelwagenService = inject(WinkelwagenService)

  ngOnInit() {
    this.productService.getDatabaseProducts();
    this.categoryService.getDatabaseCategories();
  }
  @Output() totaalprijsevent = new EventEmitter<void>()
  @Output() categorielistEvent  = new EventEmitter<void>()


  emittotaalprijsevent(){
    this.totaalprijsevent.emit()
  }

  protected list(){
    console.log(this.productList);
  }

  selectedAantal: number= 1;

  protected productList = this.productservices.productList;
  // protected categorielist = this.productservices.categoryList

  // update = computed(() => (this.categorielist = this.productservices.getCategorylist()), this.printlist());

  // public getProductList() {
  //   this.productList = this.productService.productList;
  // }



  printlist(){
    console.log('too'+ this.categorielist());
  }

  addToCart(product: string, price: number, quantity: number, PhotoIMG: string) {
    // console.log(this.selectedAantal)
    // console.log(product);
    // console.log('$'+price);
    this.winkelwagenService.addToCart(product,quantity,price, PhotoIMG );
    this.winkelwagenService.showCart()

  }




}

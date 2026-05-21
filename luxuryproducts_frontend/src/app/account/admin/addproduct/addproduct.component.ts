import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { TranslatePipe } from '@ngx-translate/core';
import { ProductinfoService } from '../../../services/productinfo.service';
import { ProductDataModel } from '../../../models/productData.model';

@Component({
  selector: 'app-addproduct',
  imports: [
    FormsModule,
    TranslatePipe
  ],
  templateUrl: './addproduct.component.html',
  styleUrl: './addproduct.component.scss'
})
export class AddproductComponent {

  constructor(private router: Router) {
    this.productService.getDatabaseProducts()
  }

  httpClient = inject(HttpClient);
  productService = inject(ProductinfoService);
  protected products = this.productService.getProducts();


  protected productName: string = ''
  protected productBeschrijving: string = ''
  protected productPrijs: number | undefined;
  protected productCategory: string = ''
  protected productImage: string = ''
  protected parentProductId: number | null = null;
  protected productColor: string = '';
  private productQuantity: number = 1;
  private previousProduct?: ProductDataModel;


  getProductData() {

    if (this.productPrijs == null) {
      this.productPrijs = 0;
    }

    const productdata = {
      productName: this.productName,
      productBeschrijving: this.productBeschrijving,
      productPrijs: this.productPrijs,
      productCategory: this.productCategory,
      productIMG: this.productImage,
      productQuantity: this.productQuantity,
      parentProductId: this.parentProductId,
      productColor: this.productColor,
    }
    this.Addproduct(productdata).subscribe(response => {
      // console.log("Added product:", response)
    })
    this.productService.getProducts()
    this.router.navigate(['/']);
  }

  Addproduct(productData: any) {
    return this.httpClient.post('http://localhost:8080/api/product', productData)

  }

  setProductVariant(productNr: string | null) {
    const nr = parseInt(productNr ?? '-1');
    const parent = this.productService.productList.find((x) => x.productNummer == nr);

    if (this.productName.includes('|')) {
      if (parent) {
        this.productName = parent.productName + ' | ' + this.productName.split('|')[1].trim();
      } else {
        this.productName = this.productName.split('|')[1].trim();
      }
    } else if (parent) {
      this.productName = parent.productName + ' | ' + this.productName;
    }

    if (parent) {
      this.productCategory = parent.category.name;
      if (!this.productBeschrijving || (this.previousProduct && this.previousProduct.productBeschrijving === this.productBeschrijving)) {
        this.productBeschrijving = parent.productBeschrijving;
      }
      if (!this.productPrijs || (this.previousProduct && this.previousProduct.productPrijs === this.productPrijs)) {
        this.productPrijs = parent.productPrijs;
      }
    }
    this.previousProduct = parent;
  }
}

import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductinfoService } from '../../../services/productinfo.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-changeproduct',
  imports: [
    FormsModule,
    TranslatePipe
  ],
  templateUrl: './changeproduct.component.html',
  styleUrl: './changeproduct.component.scss'
})
export class ChangeproductComponent {
  productService = inject(ProductinfoService)
  httpClient = inject(HttpClient);
  constructor(private router: Router,) {
    this.productService.getDatabaseProducts()

  }

  protected products = this.productService.getProducts();

  protected gekozenProductID: any;
  protected gekozenProductName: any;
  protected gekozenProductBeschrijving: any;
  protected gekozenProductPrijs: any;
  protected gekozenProductCategory: any;
  protected gekozenProductIMG: any;
  protected gekozenProductColor: any;

  changeproduct: string | undefined;

  public gekozenProductData() {
    if (!this.changeproduct) {
      return;
    }

    const idPieces = this.changeproduct.split('/');
    let selectedProduct = this.productService.productList.find(p => p.productNummer === parseInt(idPieces[0]));
    if (idPieces.length > 1) {
      selectedProduct = selectedProduct?.productVariants.find(p => p.productNummer === parseInt(idPieces[1]));
    }

    if (selectedProduct) {
      this.gekozenProductName = selectedProduct.productName;
      this.gekozenProductBeschrijving = selectedProduct.productBeschrijving;
      this.gekozenProductPrijs = selectedProduct.productPrijs;
      this.gekozenProductCategory = selectedProduct.category;
      this.gekozenProductIMG = selectedProduct.productIMG;
      this.gekozenProductID = selectedProduct.productNummer;
      this.gekozenProductColor = selectedProduct.productColor;
    }
  }


  addproductData() {
    const productdata = {
      productNummer: this.gekozenProductID,
      productName: this.gekozenProductName,
      productBeschrijving: this.gekozenProductBeschrijving,
      productPrijs: this.gekozenProductPrijs,
      productCategory: this.gekozenProductCategory,
      productIMG: this.gekozenProductIMG,
      productQuantity: 1,
      productColor: this.gekozenProductColor,
    }
    this.changeProduct(this.gekozenProductID, productdata).subscribe(response => {
      // console.log("changed product:", response)
    })
    this.productService.getProducts()
    this.router.navigate(['/']);
  }

  changeProduct(id: any, productdata: any) {
    // console.log(id)
    return this.httpClient.put('http://localhost:8080/api/product/' + id, productdata)
  }


}

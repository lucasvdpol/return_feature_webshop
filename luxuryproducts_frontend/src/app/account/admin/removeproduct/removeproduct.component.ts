import {Component, inject} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {ProductinfoService} from '../../../services/productinfo.service';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-removeproduct',
  imports: [
    FormsModule,
    TranslatePipe
  ],
  templateUrl: './removeproduct.component.html',
  styleUrl: './removeproduct.component.scss'
})
export class RemoveproductComponent {

  constructor(private router: Router,) {
    this.productService.getDatabaseProducts()
  }

  productService = inject(ProductinfoService)
  httpClient = inject(HttpClient);
  protected products = this.productService.getProducts();

  removeProductId: number = 0

  public delete(){
    if (this.removeProductId !== 0){
      // console.log("removeproduct");
      // console.log(this.removeProductId);

      this.RemoveProduct(this.removeProductId).subscribe(response => {
        // console.log("Product verwijderd:", response)
      })
      this.productService.getProducts()
      this.router.navigate(['/']);
    }
    }


  RemoveProduct(id:number){
    return this.httpClient.delete('http://localhost:8080/api/product/'+ id)
  }
}

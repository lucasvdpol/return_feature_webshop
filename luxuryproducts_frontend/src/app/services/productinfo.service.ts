import {DestroyRef, inject, Injectable, signal,} from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ProductDataModel } from '../models/productData.model';
import {Category} from "../models/Category.model";
import {CategoryService} from "./category.service";


@Injectable({
  providedIn: 'root'
})
export class ProductinfoService {

  private apiUrl = 'http://localhost:8080/api/product';
  private httpClient = inject(HttpClient);
  private categoryService = inject(CategoryService)

  public productList: ProductDataModel[] = [];
  protected products = signal<ProductDataModel[]>([]);
  protected shownProducts = signal<ProductDataModel[]>([]);
  private destroyRef = inject(DestroyRef);


  // public getProducts(): Observable<ProductDataModel[]> {
  //   // console.log("Fetching products...");
  //   return this.httpClient.get<ProductDataModel[]>(this.apiUrl);
  // }

  public getDatabaseProducts() {
    let url = "http://localhost:8080/api/product";

    const subscription = this.httpClient.get<ProductDataModel[]>(url).subscribe(
        {
          next: (responseData) => {
            let databaseProducts: ProductDataModel[] = [];
            for (let product of responseData) {
              databaseProducts.push(product);
            }
            this.products.set(databaseProducts);
            this.shownProducts.set(databaseProducts);
            console.log(this.shownProducts())

          }
        }
    );
    this.destroyRef.onDestroy(() =>
        subscription.unsubscribe())
  }

  public getProducts() {
    return this.products;
  }

  public getShownProducts() {
    return this.shownProducts;
  }

  public bekijkList() {
  }

  public categoryList = this.categoryService.getCategories()
  // update: any = signal(0)

  addAll() {

  }

  addCategories(category: Category) {
    if (this.categoryList().includes(category)) {
      const index = this.categoryList().indexOf(category);
      if (index > -1) {
        this.categoryList().splice(index, 1);
      }
    }
    else {
      this.categoryList().push(category);
      // this.update +=1;
    }
  }

  getCategorylist() {
    return this.categoryList;
  }

  forceRecompute() {
  }

  filteredProducts = [...this.productList];

  public filterProductsBySearch(searchQuery: string) {
    if (searchQuery.trim().length > 0) {
      const filteredProducts = this.shownProducts().filter(product =>
          product.productName.toLowerCase().includes(searchQuery) ||
          product.category.name.toLowerCase().includes(searchQuery)
      );
      this.shownProducts.set(filteredProducts);
    } else {
      this.shownProducts.set(this.products());
    }
  }

  public filterByCategory(categoryList: string[]) {
    if (categoryList.includes("All")) {
      this.shownProducts.set(this.products());
    } else {
      const filteredProducts = this.products().filter(p =>
          categoryList.includes(p.category.name)
      );
      this.shownProducts.set(filteredProducts);
    }
  }


}



import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {OrderDataModel} from '../models/OrderDataModel';
import {LoginService} from "../services/login.service";

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  loginService = inject(LoginService);

  private apiUrl = 'http://localhost:8080/api/orderEntity';
  private httpClient = inject(HttpClient);

  public giftcardlist: {
    orderId: number;
    giftcards: { productName: string; code: string }[];
  }[] = [];




  // Bijv. in account.component.ts of iets dergelijks
  public getGiftcardsForProduct(orderId: number, productName: string): string[] {
    const entry = this.giftcardlist.find(g => g.orderId === orderId);
    console.log("test2: " + this.giftcardlist)
    if (!entry) return [];

    return entry.giftcards

        .filter(g => g.productName === productName)
        .map(g => g.code);
  }





  public getOrderss(): void {
    const apiUrl = 'http://localhost:8080/api/orderEntity/' + this.loginService.email;
    this.httpClient.get<OrderDataModel[]>(apiUrl).subscribe((data) => {
      this.orderlist = data;
    });
  }



  // { dit
  //



  public orderListTest: any[] = [];


  public getOrders(api: any): Observable<OrderDataModel[]> {
    // console.log("Fetching orders...");
    return this.httpClient.get<OrderDataModel[]>(api);
  }


  //
  // } dit

  orderlist: OrderDataModel[] =[]





}

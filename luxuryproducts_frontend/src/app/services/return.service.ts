import {DestroyRef, inject, Injectable, signal} from '@angular/core';
import {OrderDataModel} from "../models/OrderDataModel";
import {HttpClient} from "@angular/common/http";
import {returnProduct} from "../models/returnProduct.model";
import {ReturnRequest} from "../models/returnRequest.model";

@Injectable({
  providedIn: 'root'
})
export class ReturnService {
  private httpClient = inject(HttpClient);
  private destroyRef = inject(DestroyRef)
  protected returnRequests = signal<ReturnRequest[]>([]);
  protected showReturnRequests = signal<ReturnRequest[]>([]);
  protected userReturnRequest = signal<ReturnRequest[]>([]);


  public returnRequest(selectedProducts: returnProduct[], orderId: number){
    const apiUrl = 'http://localhost:8080/api/return';
    this.httpClient.post(apiUrl, {
      orderId: orderId,
      items: selectedProducts

    }, { responseType: 'text' }).subscribe()
  }

  protected getAllReturnRequests(){
    const apiURL = 'http://localhost:8080/api/return';
    const subscription = this.httpClient.get<ReturnRequest[]>(apiURL).subscribe(
        {
          next: (responseData) =>{
            let databaseRequests : ReturnRequest[] = [];
            for (let item of responseData){
              databaseRequests.push(item);
              console.log(databaseRequests);
            }
            this.returnRequests.set(databaseRequests);
            this.showReturnRequests.set(databaseRequests);
          }
        }
    );
    this.destroyRef.onDestroy(() =>
        subscription.unsubscribe())
  }

  public getReturnRequests(){
    this.getAllReturnRequests();
    return this.returnRequests;
  }

  public getShownReturnRequests(){
    this.getAllReturnRequests();
    return this.showReturnRequests;
  }

  public getUserReturnRequest(){
    this.getAllUserReturnRequest();
    return this.userReturnRequest;
  }

  public acceptReturnRequest(returnRequestId: number){
    const apiURL = 'http://localhost:8080/api/return/accept/' + returnRequestId;
    this.httpClient.post(apiURL, {}, { responseType: 'text' }).subscribe({
      next: () => this.getAllReturnRequests()
    });
  }


  public declineReturnRequest(returnRequestId: number){
    const apiURL = 'http://localhost:8080/api/return/decline/' + returnRequestId;
    this.httpClient.post(apiURL, {}, { responseType: 'text' }).subscribe({
      next: () => this.getAllReturnRequests()
    });
  }

  public getAllUserReturnRequest(){
    const apiURL = 'http://localhost:8080/api/return/user';
    this.httpClient.get<ReturnRequest[]>(apiURL).subscribe(
        {
          next: (responseData) =>{
            let databaseRequests : ReturnRequest[] = [];
            for (let item of responseData){
              databaseRequests.push(item);
            }
            this.userReturnRequest.set(databaseRequests);
          }
        }
    )
  }

  public filterReturnsBySearch(searchQuery: string) {
    if (searchQuery.trim().length > 0) {
      const filteredReturns = this.returnRequests().filter(request =>
          request.order.customerName.toLowerCase().includes(searchQuery) ||
          request.order.id.toString().includes(searchQuery) ||
          request.order.address.toLowerCase().includes(searchQuery)
      );
      this.showReturnRequests.set(filteredReturns);
    } else {
      this.showReturnRequests.set(this.returnRequests());
    }
  }

  public getReturnLabel(returnRequestId: number){
    this.httpClient.get('http://localhost:8080/api/return/label/' + returnRequestId, { responseType: 'blob' })
        .subscribe(blob => {
          const fileURL = URL.createObjectURL(blob);
          window.open(fileURL);
        });

  }

}

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AanaccountService {

  constructor() { }

  private route: boolean = false;

  getRoute() {
    return this.route;
  }

  changeRouting() {
    this.route = true;
  }
}

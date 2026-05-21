import {Component, inject} from '@angular/core';
import {TranslatePipe} from "@ngx-translate/core";
import {DatePipe, NgClass} from "@angular/common";
import {ReturnService} from "../../services/return.service";

@Component({
  selector: 'app-returnedproducts',
  imports: [
    TranslatePipe,
    NgClass,
    DatePipe
  ],
  templateUrl: './returnedproducts.component.html',
  styleUrl: './returnedproducts.component.scss'
})
export class ReturnedproductsComponent {
  private returnService = inject(ReturnService);
  protected returnRequests = this.returnService.getUserReturnRequest();

  onClickLabel(returnRequestId: number){
    this.returnService.getReturnLabel(returnRequestId);
  }
}

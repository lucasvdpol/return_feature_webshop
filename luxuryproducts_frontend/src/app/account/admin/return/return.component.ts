import {Component, inject} from '@angular/core';
import {ReturnService} from "../../../services/return.service";
import {DatePipe} from "@angular/common";
import {TranslatePipe} from "@ngx-translate/core";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-return',
  imports: [
    DatePipe,
    TranslatePipe,
    FormsModule
  ],
  templateUrl: './return.component.html',
  styleUrl: './return.component.scss'
})
export class ReturnComponent {
  private returnService = inject(ReturnService);
  protected returnRequests = this.returnService.getShownReturnRequests();
  searchQuery: string = '';


  onDeclineReturn(returnRequestId: number) {
    this.returnService.declineReturnRequest(returnRequestId);
  }

  onAcceptRequest(returnRequestId: number) {
    this.returnService.acceptReturnRequest(returnRequestId);
  }

  onSearch(){
    this.returnService.filterReturnsBySearch(this.searchQuery);
  }



}

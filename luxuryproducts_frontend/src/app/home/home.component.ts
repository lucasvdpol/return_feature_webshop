import { Component } from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {ProductinfoComponent} from "../productinfo/productinfo.component";
import {SidebarComponent} from "../sidebar/sidebar.component";

@Component({
  selector: 'app-home',
    imports: [
        NavbarComponent,
        ProductinfoComponent,
        SidebarComponent
    ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}

import {Component, inject} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import translationsEN from "../../public/i18n/en.json";
import translationsNL from "../../public/i18n/nl.json";
import {CheckoutService} from "./services/checkout.service";
import {WinkelwagenService} from "./services/winkelwagen.service";


@Component({
  selector: 'app-root',
  imports: [

    RouterOutlet
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'Webshop';

  private translateService = inject(TranslateService);

  constructor(public checkoutService: CheckoutService, public winkelmandService: WinkelwagenService) {
    this.initialiseTranslateService();
    (window as any).checkoutService = this.checkoutService;
    (window as any).winkelmandService = this.winkelmandService;
  }

  private initialiseTranslateService() {
    this.translateService.addLangs(['nl', 'en']);
    this.translateService.setTranslation('en', translationsEN)
    this.translateService.setTranslation('nl', translationsNL)
  }
}

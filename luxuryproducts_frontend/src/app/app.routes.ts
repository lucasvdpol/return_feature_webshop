import { Routes } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {AccountComponent} from './account/account.component';
import {CheckoutComponent} from './checkout/checkout.component';
import {ReturnComponent} from "./return/return.component";
import {ConfirmationComponent} from "./return/confirmation/confirmation.component";
import {GiftcardoverzichtComponent} from "./giftcardoverzicht/giftcardoverzicht.component";
import {AccountgiftcardComponent} from "./account/accountgiftcard/accountgiftcard.component";
import {SearchgiftcardComponent} from "./account/admin/searchgiftcard/searchgiftcard.component";
import {MakegiftcardComponent} from "./account/admin/makegiftcard/makegiftcard.component";
import {RemoveproductComponent} from "./account/admin/removeproduct/removeproduct.component";
import {ChangeproductComponent} from "./account/admin/changeproduct/changeproduct.component";
import {AddproductComponent} from "./account/admin/addproduct/addproduct.component";
import {AanaccountvoegencomponentComponent} from "./aanaccounttoevoegen/aanaccounttoevoegen.component";
import {AccountdataComponent} from "./account/accountdata/accountdata.component";

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'account',
    // canMatch:
    component: AccountComponent,
  },
  {
    path: 'checkout',
    component: CheckoutComponent,
  },
  {
    path: 'addproduct',
    component: AddproductComponent,
  },
  {
    path: 'changeproduct',
    component: ChangeproductComponent,
  },
  {
    path: 'removeproduct',
    component: RemoveproductComponent,
  },
  {
    path: 'makegiftcard',
    component: MakegiftcardComponent
  },
  {
    path: 'searchgiftcard',
    component: SearchgiftcardComponent
  },
  {
    path: 'return/order/:orderId',
    component: ReturnComponent
  },
  {
    path: 'return/confirmation',
    component: ConfirmationComponent
  },
  {
    path: 'giftcard',
    component: GiftcardoverzichtComponent
  },
  {
    path: 'accountgiftcards',
    component: AccountgiftcardComponent
  },
  {
    path: 'aanaccountvoegen',
    component: AanaccountvoegencomponentComponent
  },
  {
    path: 'accountdata',
    component: AccountdataComponent
  }
];

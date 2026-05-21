import { Injectable, signal} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class WinkelwagenService {


  cartItems: { name: string, quantity: number, price: number, PhotoIMG: string }[] = [];
  giftcardItems: { name: string, quantity: number, price: number, PhotoIMG: string }[] = [];


  // totaalprice?: number = 0;

  totaalprice = signal(0);


  getCartItems() {
    return this.cartItems;
  }

  getGiftcardItems() {
    return this.giftcardItems;
  }

  isCartVisible = false;


  constructor() {
  }


  getCombinedTotalPrice(): number {
    const productTotal = this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
    const giftcardTotal = this.giftcardItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
    return productTotal + giftcardTotal;
  }

  updateTotalPrice(): void {
    this.totaalprice.set(this.getCombinedTotalPrice())
  }


  showCart() {
    this.isCartVisible = true;
  }

  hideCart() {
    this.isCartVisible = false;
  }

//   addToCart(product: string, quantity: number, price: number) {
//     if (cartItem.lenth < 0 ) {
//       for (let item of this.cartItems) {
//         if (item.name === product) {
//           console.log('ja');
//           item.quantity += quantity;}
//
//         else{
//           this.cartItems.push({ name: product, quantity: quantity, price: price })
//         }
//     }
//       else{
//         this.cartItems.push({ name: product, quantity: quantity, price: price })
//
//       }
//
//       this.calculateTotalPrice();
//     }
//   }
// }
// }

  addToCart(product: string, quantity: number, price: number, PhotoIMG: string) {
    quantity = Number(quantity);
    if (this.cartItems.length > 0) {
      let gevonden = false;

      for (let item of this.cartItems) {
        if (item.name === product) {
          item.quantity += quantity;
          gevonden = true;
          break;
        }
      }

      if (!gevonden) {
        this.cartItems.push({name: product, quantity: quantity, price: price, PhotoIMG: PhotoIMG});
      }
    } else {
      this.cartItems.push({name: product, quantity: quantity, price: price, PhotoIMG: PhotoIMG});
    }

    this.getCombinedTotalPrice();
    this.updateTotalPrice();
  }

  addGiftcardToCart(giftcardDescription: string, giftcardPrice: number, giftcardQuantity: number, giftcardImg: string ) {
    giftcardQuantity = Number(giftcardQuantity);
    if (this.cartItems.length > 0) {
      let gevonden = false;

      for (let giftcardItem of this.giftcardItems) {
        if (giftcardItem.name === giftcardDescription) {
          giftcardItem.quantity += giftcardQuantity;
          gevonden = true;
          break;
        }
      }

      if (!gevonden) {
        this.giftcardItems.push({name: giftcardDescription, quantity: giftcardQuantity, price: giftcardPrice, PhotoIMG: giftcardImg});
      }
    } else {
      this.giftcardItems.push({name: giftcardDescription, quantity: giftcardQuantity, price: giftcardPrice, PhotoIMG: giftcardImg});
    }

    this.getCombinedTotalPrice();
    this.updateTotalPrice();
  }


  RemoveInhoudCart() {
    if (this.cartItems.length > 0) {
      for (let i = this.cartItems.length - 1; i >= 0; i--) {
        this.cartItems.splice(i, 1);
      }
    }
    if (this.giftcardItems.length > 0) {
      for (let i = this.giftcardItems.length - 1; i >= 0; i--) {
        this.giftcardItems.splice(i, 1);
      }
    }
    this.getCombinedTotalPrice();
    this.updateTotalPrice();
  }




  itemPlus(item: any) {
    item.quantity = item.quantity + 1;
    this.getCombinedTotalPrice();
    this.updateTotalPrice();

  }
  itemGiftcardPlus(item: any) {
    item.quantity = item.quantity + 1;
    this.getCombinedTotalPrice();
    this.updateTotalPrice();

  }

  itemMin(item: any){
    item.quantity = item.quantity - 1;
    if( item.quantity == 0 ){
      this.itemRemove(item);
    }
    this.getCombinedTotalPrice();
    this.updateTotalPrice();

  }
  itemGiftcardMin(item: any){
    item.quantity = item.quantity - 1;
    if( item.quantity == 0 ){
      this.itemGiftcardRemove(item);
    }
    this.getCombinedTotalPrice();
    this.updateTotalPrice();

  }

  itemRemove(item: any) {
    const index = this.cartItems.findIndex(cartItem => cartItem.name === item.name);

    if (index !== -1) {
      this.cartItems.splice(index, 1);
    }
    this.getCombinedTotalPrice();
    this.updateTotalPrice();
  }
  itemGiftcardRemove(item: any) {
    const index = this.giftcardItems.findIndex(cartItem => cartItem.name === item.name);

    if (index !== -1) {
      this.giftcardItems.splice(index, 1);
    }
    this.getCombinedTotalPrice();
    this.updateTotalPrice();
  }
}

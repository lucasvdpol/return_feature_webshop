export interface OrderDataModel{
  id: number;
  customerName: string;
  orderDate: string;
  totalPrice: number;
  address: string;
  postcode: string;
  city: string;
  country: string;
  winkelwagen:[{
    productName: string;
    quantity: number;
    price: number;
  }]
  requestedReturn: boolean;
}

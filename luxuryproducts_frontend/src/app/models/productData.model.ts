import {Category} from "./Category.model";

export interface ProductDataModel {
  // Backend
  productNummer: number,
  productName: string,
  productBeschrijving: string,
  productPrijs: number;
  category: Category;
  productIMG: string;
  productQuantity: number;
  productVariants: ProductDataModel[]
  productColor: string

  // Local
  chosenVariant?: ProductDataModel
}

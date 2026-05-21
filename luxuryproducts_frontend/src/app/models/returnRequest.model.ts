import {OrderDataModel} from "./OrderDataModel";
import {returnProduct} from "./returnProduct.model";

export interface ReturnRequest {
    id: number;
    order: OrderDataModel;
    returnItems: returnProduct[];
    status: string,
    createdAt: Date,
    updatedAt: Date,
}
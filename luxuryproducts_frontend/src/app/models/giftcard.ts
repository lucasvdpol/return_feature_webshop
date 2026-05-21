export interface Giftcard {
    id: number;
    giftcardCode: string;
    date: Date;
    endDate: Date;
    price: number;
    geschiedenis: string;
    user: {
        id: number;
        email: string;
        password: string;
    }
    blocked: boolean;
    editing: boolean;
    newEndDate: string;
    showEmailInput: boolean;
    newEmail: string;
}
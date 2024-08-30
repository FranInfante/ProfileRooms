import { Extra } from "./extra";

export interface Office {
    id: number,
    province: string,
    city: string,
    postcode: string,
    address: string,
    extras: Extra[];
}

import { Extra } from "./extra";
import { Office } from "./office";
import { Schedule } from "./schedule";

export interface Room {
    id: number;
    nameRoom: string;
    size: string;
    capacity: string;
    priceHour: number;
    availability: string;
    description: string;
    pictureList: string;
    available: boolean;
    office: Office;
    schedules: Schedule;
}
import { environment } from "../../../environments/environment";

export const ROOM_API_URL = environment.endpointUrl + 'rooms';

export const ROOM_ROUTES = {
  list: () => `${ROOM_API_URL}`,
  listByCity: (cityName: string) => `${ROOM_API_URL}/officeCity/${cityName}`,
  listByPrice: (pricePerHour: number) => `${ROOM_API_URL}/priceHour/${pricePerHour}`,
  get: (id: number) => `${ROOM_API_URL}/${id}`,
  create: () => `${ROOM_API_URL}`,
  update: (idroom:number) => `${ROOM_API_URL}/${idroom}`,
  delete: (id: number) => `${ROOM_API_URL}/${id}`,
  getSchedule: (id: number) => `${ROOM_API_URL}/${id}/schedule`,
};

import { environment } from "../../../environments/environment";

export const BOOKINGS_API_URL = environment.endpointUrl + 'bookings';

export const BOOKINGS_ROUTES = {
  list: () => `${BOOKINGS_API_URL}`,
  get: (id: number) => `${BOOKINGS_API_URL}/${id}`,
  create: () => `${BOOKINGS_API_URL}`,
  update: (id: number) => `${BOOKINGS_API_URL}/${id}`,
  delete: (id: number) => `${BOOKINGS_API_URL}/${id}`,
  findByBookingDate: (date : string) => `${BOOKINGS_API_URL}/date?bookingsDate=${date}`,
  findByRoomId: (roomId: number) => `${BOOKINGS_API_URL}/room/${roomId}`,
  findByUserId: (userId: number) => `${BOOKINGS_API_URL}/user/${userId}`,
  findHours: (roomid:number) => `${BOOKINGS_API_URL}/room/${roomid}/times`,
 
};

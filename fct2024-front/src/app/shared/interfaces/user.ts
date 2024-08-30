export interface User {
  id?: number;
  name: string;
  surname: string;
  username: string;
  password: string;
  email: string;
  dni: string;
  phone: string;
  postcode: string;
  address: string;
  role?: string;
  available?: boolean;
}

import { environment } from "../../../environments/environment";

export const OFFICE_API_URL = environment.endpointUrl + 'offices';

export const OFFICE_ROUTES = {
  list: () => `${OFFICE_API_URL}`,
  get: (id: number) => `${OFFICE_API_URL}/${id}`,
  create: () => `${OFFICE_API_URL}`,
  update: (id: number) => `${OFFICE_API_URL}/${id}`,
  delete: (id: number) => `${OFFICE_API_URL}/${id}`,
  findByProvince: (province: string) => `${OFFICE_API_URL}/province/${province}`,
  getExtrasByOfficeId: (id:number) => `${OFFICE_API_URL}/extras/${id}`,
};

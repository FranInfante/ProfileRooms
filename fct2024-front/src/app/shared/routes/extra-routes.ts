import { environment } from "../../../environments/environment";

export const EXTRA_API_URL =  environment.endpointUrl + 'extras';

export const EXTRA_ROUTES = {
  list: () => `${EXTRA_API_URL}`,
  get: (id: number) => `${EXTRA_API_URL}/${id}`,
  create: () => `${EXTRA_API_URL}`,
  update: () => `${EXTRA_API_URL}`,
  delete: (id: number) => `${EXTRA_API_URL}/${id}`,

};

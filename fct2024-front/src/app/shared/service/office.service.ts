import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Office } from '../interfaces/office';
import { Observable } from 'rxjs';
import { OFFICE_ROUTES } from '../routes/office-routes';
import { Extra } from '../interfaces/extra';

@Injectable({
  providedIn: 'root'
})
export class OfficeService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getOffices(): Observable<Office[]> {
    return this.http.get<Office[]>(OFFICE_ROUTES.list());
  }

  getOfficeById(id: number): Observable<Office> {
    return this.http.get<Office>(OFFICE_ROUTES.get(id));
  }

  getOfficesByProvince(province: string): Observable<Office[]> {
    return this.http.get<Office[]>(OFFICE_ROUTES.findByProvince(province));
  }

  createOffice(office: Office): Observable<Office> {
    return this.http.post<Office>(OFFICE_ROUTES.create(), office, this.httpOptions);
  }

  deleteOffice(id: number) {
    return this.http.delete<Office>(OFFICE_ROUTES.delete(id), this.httpOptions);
  }

  updateOffice(id: number, office: Office): Observable<Office> {
    return this.http.put<Office>(OFFICE_ROUTES.update(id), office, this.httpOptions);
  }

  getExtraByOfficeId(id:number): Observable<Extra[]>{
    return this.http.get<Extra[]>(OFFICE_ROUTES.getExtrasByOfficeId(id));  
  }

}

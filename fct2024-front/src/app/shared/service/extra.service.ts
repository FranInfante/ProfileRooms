import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Extra } from '../interfaces/extra';
import { EXTRA_ROUTES } from '../routes/extra-routes';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ExtraService {
  private http = inject(HttpClient);

  getAllExtras(): Observable<Extra[]> {
    return this.http.get<Extra[]>(EXTRA_ROUTES.list());
  }

  findExtraById(id: number): Observable<Extra> {
    return this.http.get<Extra>(EXTRA_ROUTES.get(id));
  }

  createExtra(extra: Extra): Observable<Extra> {
    return this.http.post<Extra>(EXTRA_ROUTES.create(), extra);
  }

  updateExtra(extra: Extra): Observable<Extra> {
    return this.http.put<Extra>(EXTRA_ROUTES.update(), extra);
  }

  deleteExtra(id: number): Observable<void> {
    return this.http.delete<void>(EXTRA_ROUTES.delete(id));
  }

}

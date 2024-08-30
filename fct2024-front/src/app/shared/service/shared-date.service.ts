import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

@Injectable({
  providedIn: 'root'
})
export class SharedDateService {
  private dateSource = new BehaviorSubject<NgbDateStruct | null>(null);
  currentDate = this.dateSource.asObservable();

  changeDate(date: NgbDateStruct) {
    this.dateSource.next(date);
  }
}

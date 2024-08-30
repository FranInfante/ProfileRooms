import { Component, OnDestroy, OnInit } from '@angular/core';
import { Extra } from '../../../../../shared/interfaces/extra';
import { ExtraService } from '../../../../../shared/service/extra.service';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { SubscriptionLike } from 'rxjs';

@Component({
  selector: 'app-extras',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './extras.component.html',
  styleUrl: './extras.component.css'
})
export class ExtrasComponent implements OnDestroy, OnInit {


  
  Extras: Extra[] | undefined;
  createForm: FormGroup | any;
  createExtraTog= false;
  subscriptions: SubscriptionLike[] = [];
  
  constructor(
    
    private extraService: ExtraService,
    private fb: FormBuilder,
  ) {
  }
  
  ngOnInit(): void {

    this.formCreateInit();
    this.getAllExtras();

  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(
      (subscription) => subscription.unsubscribe());
  }

  cancelCreate() {
    this.createExtraTog=!this.createExtraTog
  }
  


  createExtraTogg() {
    this.createExtraTog=!this.createExtraTog
  
  }
  
  getAllExtras() {
    this.subscriptions.push(this.extraService.getAllExtras().subscribe(allExtras => {
      this.Extras = allExtras;
    }));
  }
  
  deleteExtraById(idextra: number) {
    this.subscriptions.push(this.extraService.deleteExtra(idextra).subscribe(() => {
      this.getAllExtras();
    }));
  }

  createExtra(){

    if(this.createForm.valid){
      this.subscriptions.push(this.extraService.createExtra(this.createForm.value).subscribe(data=>{

        this.getAllExtras();
      }));
    }

    this.getAllExtras();
  }

  formCreateInit(): void {
    this.createForm = this.fb.group({
      name: [],
      description:[],
      priceHour:[]
    });
  }
}
import { Router, RouterModule } from '@angular/router';
import { User } from './../../../shared/interfaces/user';
import { UserService } from './../../../shared/service/user.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgIf } from '@angular/common';
import { SubscriptionLike } from 'rxjs';
import { ASSET_URLS, TOAST_MSGS } from '../../../shared/components/constants';
import { ToastService } from '../../../shared/service/toast.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule, NgIf],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit, OnDestroy {
  userForm!: FormGroup;
  formvalid = false;
  subscriptions: SubscriptionLike[] = [];
  ASSET_URLS: typeof ASSET_URLS = ASSET_URLS;

  constructor(private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
    private toastService: ToastService
  ) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(
      (subscription) => subscription.unsubscribe());
  }

  initializeForm(): void {
    this.userForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(5)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      email: ['', [Validators.required, Validators.email]],
      role: 'USER'
    })
  }

  createUser() {
    if (this.userForm.valid) {
      const user: User = {
        name: this.userForm.value.name,
        surname: this.userForm.value.surname,
        username: this.userForm.value.username,
        password: this.userForm.value.password,
        email: this.userForm.value.email,
        dni: this.userForm.value.dni,
        phone: this.userForm.value.phone,
        postcode: this.userForm.value.postcode,
        address: this.userForm.value.address,
      };


      this.subscriptions.push(this.userService.createUser(user).subscribe(response => {
        this.formvalid = true;
        setTimeout(() => {
          this.formvalid = false;
        }, 2000)
        this.router.navigate([ASSET_URLS.login]);
        this.toastService.showToast(TOAST_MSGS.register);
        this.userForm.reset();
      }));
    } if (this.userForm.invalid) {
      this.markFormGroupTouched(this.userForm);
      return;
    }
  }

  markFormGroupTouched(formGroup: FormGroup) {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();
      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }
}

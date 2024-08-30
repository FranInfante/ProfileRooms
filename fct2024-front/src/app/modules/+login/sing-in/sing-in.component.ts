import { ToastService } from './../../../shared/service/toast.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from './../../../shared/service/user.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { NgIf } from '@angular/common';
import { SubscriptionLike } from 'rxjs';
import { ASSET_URLS, MSG, TOAST_MSGS } from '../../../shared/components/constants';

@Component({
  selector: 'app-sing-in',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, NgIf],
  templateUrl: './sing-in.component.html',
  styleUrl: './sing-in.component.css'
})
export class SingInComponent implements OnInit, OnDestroy {
  loginForm!: FormGroup;
  loginError: string | null = null;
  subscription?: SubscriptionLike;
  ASSET_URLS: typeof ASSET_URLS = ASSET_URLS;

  constructor(private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
    private toastService: ToastService) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  initializeForm(): void {
    this.loginForm = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    const loginData = this.loginForm.value;
    this.subscription = this.userService.loginUser(loginData.email, loginData.password).subscribe(user => {
      if (user) {
        this.userService.setUser(user);
        this.router.navigate([ASSET_URLS.home]);
        this.toastService.showToast(TOAST_MSGS.login);
      } else {
        this.loginError = MSG.failedPassword;
      }
    },
      error => {
        if (error === MSG.failedPassword) {
          this.loginError = error;
        } else {
          this.loginError = MSG.unknownLoginError
        }
      }
    );
  }
}

import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../../shared/service/user.service';
import { User } from '../../../shared/interfaces/user';
import { NgIf } from '@angular/common';
import { SubscriptionLike } from 'rxjs';

@Component({
  selector: 'app-user-information',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, NgIf],
  templateUrl: './user-information.component.html',
  styleUrl: './user-information.component.css'
})
export class UserComponent implements OnInit, OnDestroy {
  userForm!: FormGroup;
  userId: number = 1;
  user!: User;
  isEditing: boolean = false;
  subscriptions: SubscriptionLike[] = [];

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.gatherUserId();
    this.initForm();
    this.loadUser();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(
      (subscription) => subscription.unsubscribe());
  }

  gatherUserId() {
    this.subscriptions.push(this.userService.userSubject.subscribe(user => {
      if (user && user.id) {
        this.userId = user?.id;
      }
    }));
  }

  initForm() {
    this.userForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      surname: new FormControl('', [Validators.required]),
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      dni: new FormControl('', [Validators.required]),
      phone: new FormControl('', [Validators.required]),
      postcode: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
      role: new FormControl('', [Validators.required]),
      available: new FormControl(true)
    });

    this.userForm.disable();
  }

  loadUser() {
    this.subscriptions.push(this.userService.getUserById(this.userId).subscribe({
      next: (data) => {
        this.user = data;
        this.userForm.patchValue(data);
      }
    }));
  }

  updateUser() {
    if (this.userForm.valid) {
      this.subscriptions.push(this.userService.updateUser(this.userId, this.userForm.value).subscribe({
        next: () => {
          this.loadUser();
          this.userForm.disable();
          this.isEditing = false;
        }
      }));
    }
  }

  deactivateAccount() {
    this.userForm.enable();
    this.userForm.get('available')!.setValue(false);
    this.updateUser();
  }

  toggleEdit() {
    this.isEditing = !this.isEditing;
    if (this.isEditing) {
      this.userForm.enable();
    } else {
      this.userForm.disable();
    }
  }
}

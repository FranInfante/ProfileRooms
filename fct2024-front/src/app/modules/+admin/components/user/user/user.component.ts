import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserService } from '../../../../../shared/service/user.service';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { User } from '../../../../../shared/interfaces/user';
import { SubscriptionLike } from 'rxjs';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent implements OnInit, OnDestroy {
  subscriptions: SubscriptionLike[] = [];
  User: User | undefined;
  Users: User[] | undefined;
  userForm!: FormGroup;
  modifyUserTog = false;
  createUserTog = false;

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.initializeForm();
    this.getAllUsers();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(
      (subscription) => subscription.unsubscribe());
  }

  initializeForm(): void {
    this.userForm = this.fb.group({
      username: [""],
      password: [""],
      name: [""],
      surname: [""],
      phone: [""],
      email: [""],
      dni: [""],
      address: [""],
      postcode: [""],
      role: [""]
    });
  }

  getAllUsers(): void {
    this.subscriptions.push(this.userService.getAllUsers().subscribe(allUsers => {
      this.Users = allUsers;
    }));
  }

  deleteUserById(userId: number): void {
    this.subscriptions.push(this.userService.deleteUser(userId).subscribe(() => {
      this.getAllUsers();
    }));
  }

  modifyUser(): void {
    if (!this.User) return;
    if (this.userForm.valid) {
      if (this.User.id != null) {
        this.subscriptions.push(this.userService.updateUser(this.User.id, this.userForm.value).subscribe(() => {
          this.modifyUserTog = false;
          this.getAllUsers();
        }));
      }
    }
  }

  modifyUserToggle(userId: number): void {
    this.getUserById(userId);
    this.modifyUserTog = !this.modifyUserTog;
    this.createUserTog = false;
  }

  modifyUserToggleButton(): void {
    this.modifyUserTog = !this.modifyUserTog;
    this.createUserTog = false;
  }

  createUserToggleButton(): void {
    this.createUserTog = !this.createUserTog;
    this.modifyUserTog = false;
  }

  createUser(): void {
    if (this.userForm.valid) {
      this.subscriptions.push(this.userService.createUser(this.userForm.value).subscribe(() => {
        this.createUserTog = false;
        this.getAllUsers();
      }));
    }
  }

  getUserById(userId: number): void {
    this.subscriptions.push(this.userService.getUserById(userId).subscribe(user => {
      this.User = user;
    }));
  }
}

import { Injectable, OnDestroy, OnInit } from '@angular/core';
import { User } from '../interfaces/user';
import { UserService } from './user.service';
import { SubscriptionLike } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnInit, OnDestroy {
  private currentUser: User | null = null;
  subscription?: SubscriptionLike;

  constructor(private userService: UserService) { }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  ngOnInit(): void {
    this.initializeUser();
  }

  initializeUser(): void {
    this.subscription = this.userService.userSubject.subscribe(user => {
      this.currentUser = user;
    });
  }

  getUser(): User | null {
    return this.currentUser;
  }

  isAdmin(): boolean {
    return this.currentUser?.role === 'ADMIN';
  }
}

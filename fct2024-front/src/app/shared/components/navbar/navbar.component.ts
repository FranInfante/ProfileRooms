import { CommonModule } from '@angular/common';
import { UserService } from './../../service/user.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ASSET_URLS } from '../constants';
import { User } from '../../interfaces/user';
import { SubscriptionLike } from 'rxjs';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {
  currentUser: User | null = null;
  showProfileLogo: boolean = false;
  showAdminMenu: boolean = false;
  logoUrl: string = ASSET_URLS.logoUrl;
  userlogo: string = "../assets/profilelogoexample/icon.png";
  subscription?: SubscriptionLike;
  ASSET_URLS: typeof ASSET_URLS = ASSET_URLS;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.initUserSubject();
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  initUserSubject(): void {
    this.subscription = this.userService.userSubject.subscribe(user => {
      this.currentUser = user;
    });
  }

  showProfileInfo(): void {
    this.showProfileLogo = true;
  }

  hideProfileInfo(): void {
    this.showProfileLogo = false;
  }

  showAdminDropdown(): void {
    this.showAdminMenu = true;
  }

  hideAdminDropdown(): void {
    this.showAdminMenu = false;
  }

  logout(): void {
    this.userService.logout();
  }
}

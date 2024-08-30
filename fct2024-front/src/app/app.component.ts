import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './shared/components/navbar/navbar.component';
import { RoomListComponent } from './modules/+showrooms/room-list/room-list.component';
import { FooterComponent } from './shared/components/footer/footer.component';
import { RoomDetailComponent } from './modules/+showrooms/room-detail/room-detail.component';
import { HttpClientModule } from '@angular/common/http';
import { SingInComponent } from './modules/+login/sing-in/sing-in.component';
import { RegisterComponent } from './modules/+login/register/register.component';
import { AdminComponent } from './modules/+admin/admin/admin.component';
import { CommonModule } from '@angular/common';
import { NgbdToastInline } from './shared/components/toast/toast.component';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [

    AdminComponent,
    RouterOutlet,
    NavbarComponent,
    RoomListComponent,
    FooterComponent,
    RoomDetailComponent,
    HttpClientModule,
    SingInComponent,
    RegisterComponent,
    CommonModule,
    NgbdToastInline
  ],
})
export class AppComponent {
  title = 'fct2024-front';
}

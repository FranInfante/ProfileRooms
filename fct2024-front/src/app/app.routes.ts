import { Routes } from '@angular/router';
import { RoomDetailComponent } from './modules/+showrooms/room-detail/room-detail.component';
import { RoomListComponent } from './modules/+showrooms/room-list/room-list.component';
import { SingInComponent } from './modules/+login/sing-in/sing-in.component';
import { RegisterComponent } from './modules/+login/register/register.component';
import { AdminComponent } from './modules/+admin/admin/admin.component';
import { UserPageComponent } from './modules/+user/user-page/user-page.component';

import { BookingAdminComponent } from './modules/+admin/components/booking-admin/booking-admin.component';

import { authGuard } from './shared/service/auth.guard';
import { HomeComponent } from './modules/+home/home.component';


export const routes: Routes = [
    {path: "", component: HomeComponent},
    {path: "rooms", component: RoomListComponent},
    {path: "profile", component: UserPageComponent},
    {path: "admin", component: AdminComponent, canActivate: [authGuard] },
    {path: "rooms/detail/:id", component: RoomDetailComponent},
    {path: "login", component: SingInComponent},
    {path: "register", component: RegisterComponent},
    {path: "admin/bookings", component: BookingAdminComponent}
];

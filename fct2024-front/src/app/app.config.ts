import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideHttpClient} from '@angular/common/http';
import { UserService } from './shared/service/user.service';
import { ExtraService } from './shared/service/extra.service';
import { RoomService } from './shared/service/room.service';
import { BookingsService } from './shared/service/bookings.service';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

export const appConfig: ApplicationConfig = {
  providers:[
    UserService,ExtraService,RoomService,BookingsService,
 provideRouter(routes),
 provideHttpClient(), provideAnimationsAsync()
     ]
};

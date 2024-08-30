import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { inject } from '@angular/core';
import { ASSET_URLS } from '../components/constants';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const login = ASSET_URLS.login;

  if (authService.isAdmin()) {
    return true;
  } else {
    router.navigate([login]);
    return false;
  }
};

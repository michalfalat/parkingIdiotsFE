import { AuthService } from '../auth/auth.service';
import { CanActivate } from '@angular/router';
import { Injectable } from '@angular/core';

@Injectable()
export class AuthGuardService implements CanActivate{

  constructor(private authService: AuthService) { }

  canActivate(){
    return this.authService.isAuthenticated();
  }
}

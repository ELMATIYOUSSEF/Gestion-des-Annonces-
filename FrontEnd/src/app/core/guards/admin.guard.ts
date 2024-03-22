import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthenticationService} from '../services/auth.service';
import {authUtils} from '../../authUtils';
import {Role} from '../models/role.enum';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private router: Router) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const user = authUtils.getAuthenticatedUser();

    if (user && user.authorities.includes(Role.ROLE_ADMIN)) {
      return true;
    } else {
      return this.router.createUrlTree(['/']);
    }
  }
}

import { Component, OnInit } from '@angular/core';
import {User} from '../core/models/auth.models';
import {authUtils} from '../authUtils';
import {Role} from '../core/models/role.enum';
import {Router} from '@angular/router';

@Component({
  selector: 'app-publisher',
  templateUrl: './publisher.component.html',
  styleUrls: ['./publisher.component.scss']
})
export class PublisherComponent implements OnInit {
  user: User | null;
  constructor(private router: Router) { }
  ngOnInit(): void {
    this.user = authUtils.getAuthenticatedUser();
  }

  isAdmin(): boolean {
    return this.user?.authorities.includes(Role.ROLE_ADMIN);
  }

  isAuthenticated(): boolean {
    return !!this.user;
  }
  logout() {
    authUtils.logout();
    this.user = null; // Clear the user data from the component
    this.router.navigate(['/']).then(() => {
      window.location.reload(); // Optionally force reloading the page
    });
  }
}

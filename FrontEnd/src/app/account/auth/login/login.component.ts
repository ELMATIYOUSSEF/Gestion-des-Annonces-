import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthenticationService } from '../../../core/services/auth.service';
import { AuthfakeauthenticationService } from '../../../core/services/authfake.service';

import { OwlOptions } from 'ngx-owl-carousel-o';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import {UserService} from '../../../pages/Announce/service/User.service';
import {User} from '../../../core/models/auth.models';
import {authUtils} from '../../../authUtils';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
/**
 * Login-2 component
 */
export class LoginComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private authenticationService: AuthenticationService,
              private userService: UserService) { }

  loginForm: FormGroup;
  submitted = false;
  error = '';
  returnUrl: string;
  user: User;

  // set the currenr year
  year: number = new Date().getFullYear();

  ngOnInit(): void {
    document.body.classList.add('auth-body-bg');
    this.loginForm = this.formBuilder.group({
      email: ['youssef@gmail.com', [Validators.required, Validators.email]],
      password: ['password', [Validators.required]],
    });

    // reset login status
    // this.authenticationService.logout();
    // get return url from route parameters or default to '/'
    // tslint:disable-next-line: no-string-literal
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }



  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  /**
   * Form submit
   */
  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      console.log('invalid login form');
      return;
    } else {
      this.authenticationService.login(this.f.email.value, this.f.password.value)
        .subscribe({
          next: () => {
            this.user = authUtils.getAuthenticatedUser();
            const userName = this.user.firstName;
            this.userService.setUserName(userName);
            this.router.navigate(['/dashboard']);
          },
          error: error => {
            if (error.status === 401) {
              this.error = 'Invalid email or password';
            } else {
              this.error = 'An unexpected error occurred. Please try again later.';
            }
          }
        });
    }
  }
}

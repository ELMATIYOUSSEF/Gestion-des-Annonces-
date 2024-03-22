import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../../../core/services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {environment} from '../../../../environments/environment';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {
  resetForm: FormGroup;
  resetPwdForm: FormGroup;
  submitted = false;
  error = '';
  success = '';
  loading = false;
  showPassword = false;

  // set the currenr year
  year: number = new Date().getFullYear();

  // tslint:disable-next-line: max-line-length
  private token: string;
  isValide = false;
  constructor(
      private formBuilder: FormBuilder,
      private route: ActivatedRoute,
      private router: Router,
      private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.resetForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
    });
    this.resetPwdForm = this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]],
    });

    this.route.queryParams.subscribe(
        (params) => {
          let token = params['token'];
          if (token) {
            this.token = token;
            this.authenticationService.validateToken(token).subscribe(
                (resp: boolean) => {
                  this.isValide = resp;
                }
            );
          }
        }
    )
  }

  get f() {
    return this.resetForm.controls;
  }

  get pwdForm() { return this.resetPwdForm.controls; }

  /**
   * On submit form
   */
  onSubmit() {
    this.success = '';
    this.error = '';
    this.submitted = true;
    if (this.resetForm.invalid) {
      return;
    }

    this.authenticationService.resetPassword(this.f.email.value).subscribe(
        () => {
          this.success = 'reset request has been send to you mail';
        },
        () => {
          this.error = 'Email No Found';
        }
    );
  }

  onResetPwd() {
    if (this.resetPwdForm.invalid) {
      return;
    }
    if (this.pwdForm.password.value !== this.pwdForm.confirmPassword.value) {
      this.error = 'Password not match.';
      return;
    }
    this.loading = true;
    this.error = '';
    this.success = '';
    this.authenticationService.resetPasswordWithToken(this.token, this.pwdForm.password.value).subscribe(
        () => {
          this.loading = false;
          this.success = 'Password reset successfully.';
        },
        error => {
          this.loading = false;
          this.error = error ? error : '';
        }
    );
  }
}

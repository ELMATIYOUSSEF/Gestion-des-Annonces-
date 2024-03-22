import { Injectable } from '@angular/core';

import { authUtils } from '../../authUtils';

import { User } from '../models/auth.models';
import {Observable, of} from 'rxjs';
import {JwtAuthenticationResponse} from '../../account/auth/jwt-authentication-response.model';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {catchError, map, mergeMap} from 'rxjs/operators';

@Injectable({ providedIn: 'root' })

export class AuthenticationService {

  private apiUrl: string = environment.baseUrl + '/api/v1/auth/';

  user: User;

  constructor(private http: HttpClient) {
  }

  /**
   * Registers the user with given details
   */
  register(firstName: string, lastName: string, email: string, password: string): Observable<JwtAuthenticationResponse> {
    return this.http.post<JwtAuthenticationResponse>(this.apiUrl + 'register', {firstName ,lastName ,email, password});
  }

  /**
   * Login user with given details
   */
  login(email: string, password: string): Observable<JwtAuthenticationResponse> {
      return this.http.post<JwtAuthenticationResponse>(this.apiUrl + 'login', {email, password})
          .pipe(
              mergeMap((response: JwtAuthenticationResponse) => {
                  // login successful if there's a jwt token in the response
                  if (response && response.accessToken && response.refreshToken) {
                      // retrieve the user info
                      return this.auth(response.accessToken).pipe(
                          mergeMap((user: User) => {
                              authUtils.setLoggedCredentials(user, response);
                              return of(response); // Return the response as an Observable
                          }),
                          catchError(error => {
                              // handle potential errors that could occur during the auth call
                              console.error('Error during authentication', error);
                              return of(null); // or throw an appropriate error
                          })
                      );
                  }
                  return of(response); // Return the response if the condition is not met
              }),
              catchError(error => {
                  // handle potential errors that could occur during the login call
                  console.error('Error during login', error);
                  return of(null); // or throw an appropriate error
              })
          );
  }

  auth(access_token: string): Observable<User> {
    return this.http.get<User>(this.apiUrl + 'auth', {headers: {Authorization: `Bearer ${access_token}`}})
      .pipe(
        map((response: User) => {
          if (response) {
            authUtils.setLoggedCredentials(response, null);
          }
          return response;
        })
      );
  }

  /**
   * forget Password user with given details
   */
  resetPassword(email: string) {
    return this.http.post(
        this.apiUrl + 'password-reset/request', {}, {params: {email}});
  }

  logout() {
      // logout the user
      authUtils.logout();
  }

    getRefreshToken(){
    return authUtils.getRefrechToken();
    }
    getAccessToken(){
        return authUtils.getAccessToken();
    }

    refreshToken(refresh_token: string): Observable<JwtAuthenticationResponse> {
      return this.http.post<JwtAuthenticationResponse>(this.apiUrl + 'token/refresh', {}, {headers: {Authorization: `Bearer ${refresh_token}`}})
        .pipe(
          map((response: JwtAuthenticationResponse) => {
            if (response) {
              authUtils.setAccessToken(response.accessToken);
            }
            return response;
          })
        );
    }

    validateToken(token: string) {
        return this.http.post<boolean>(
            this.apiUrl + 'password-reset/validate', {}, {params: {token}});
    }


    resetPasswordWithToken(token: string, password: string) {
        return this.http.post<void>(this.apiUrl + 'password-reset/reset', {}, {params: {token, newPassword: password}});
    }
}


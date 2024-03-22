import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private userNameSource = new BehaviorSubject<string>('');

    userName$ = this.userNameSource.asObservable();

    constructor() {}

    setUserName(name: string) {
        this.userNameSource.next(name);
    }
}

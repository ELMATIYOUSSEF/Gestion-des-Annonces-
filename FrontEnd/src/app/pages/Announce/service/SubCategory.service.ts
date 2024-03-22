import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import {environment} from '../../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class SubCategoryService {
    private resourceUrl = environment.baseUrl + '/api/v1/subcategorys';

    constructor(private http: HttpClient) {
    }

    getAllSubCategories(): Observable<any> {
        return this.http.get(`${this.resourceUrl}`);
    }

    createSubCategory(subCategory: string, id: number): Observable<any> {
        return this.http.post(`${this.resourceUrl}?subCategory=${subCategory}&id=${id}`, {});
    }
}

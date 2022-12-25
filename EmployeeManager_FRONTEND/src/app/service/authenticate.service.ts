import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {

  private apiServerURL = environment.apiBaseURL;

  constructor(private http: HttpClient) { }

  public authenticate() : Observable<string> {
    return this.http.post<string>(`${this.apiServerURL}/app/authenticate`, environment.user, {responseType: 'text' as 'json'});
  }

}

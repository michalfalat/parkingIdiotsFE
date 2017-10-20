import { Observable } from 'rxjs/Rx';
import { environment } from '../../../environments/environment';
import { Headers, Http, RequestOptions, Response } from '@angular/http';
import { RegisterUser } from '../../models/RegisterUserModel';
import { LoginUser } from '../../models/LoginUserModel';
import { Injectable } from '@angular/core';

@Injectable()
export class AuthService {

  private token: string;
  private username: string;
  constructor(private http: Http) {
    this.token = localStorage.getItem('id_token');
    this.username = localStorage.getItem('username');
   }

  public register(user: RegisterUser){
    return this.http.post(environment.URL_REGISTER, user);
  }

  public login(user: LoginUser) {
    const headers = new Headers({ 'Content-Type': 'application/x-www-form-urlencoded' });
    const options = new RequestOptions({ headers: headers });
     
    const request = "grant_type=password&username=" + user.Email + "&password=" + user.Password;
    return this.http.post(environment.URL_LOGIN, request, options).map((res: Response) => {

      const body: any = res.json();

      // Sign in successful if there's an access token in the response.  
      if (typeof body.access_token !== 'undefined') {

        console.log('saving token..');
        console.log(body);
        // Stores access token in local storage to keep user signed in.  
        localStorage.setItem('id_token', body.access_token);
        localStorage.setItem('username', body.userName);
        this.token = body.access_token;
        this.username = body.userName;
        // Stores refresh token in local storage.  
        localStorage.setItem('refresh_token', body.refresh_token);
      }

    }).catch((error: any) => {

      // Error on post request.  
      return Observable.throw(error);

    });
  }
  

  public logout(){
    //POST to API
    localStorage.removeItem('id_token');
    localStorage.removeItem('username');
    this.token = null;
  }
  public isAuthenticated(){
    return  this.token != null
  }

  public getUserName(){
    return this.username;
  }

 
}

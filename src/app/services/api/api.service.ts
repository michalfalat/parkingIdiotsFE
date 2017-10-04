import { RegisterUser } from '../../models/RegisterUserModel';
import { Http } from '@angular/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable()
export class ApiService {

  constructor(private http: Http) { }

  

  public values(){
    return this.http.get(environment.URL_VALUES);
  }

}

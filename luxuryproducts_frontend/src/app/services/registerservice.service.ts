import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginService} from './login.service';
import {Token} from '../models/token';
import {tap} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterserviceService {
  private token: string | null = null;
  private httpClient = inject(HttpClient);
  inlogService = inject(LoginService);

  isRegisterPopupVisible = false;
  isregistered: boolean = false;

  onlogin() {
    this.isregistered = true;
  }


  getIsloggedin() {
    return this.isregistered;
  }



  constructor() { }

  onNieuwRegister( gebruikerdata: any) {
    return this.httpClient.post<Token>("http://localhost:8080/api/auth/register", gebruikerdata)
  }

  onRegisterData(email: any, password: any) {
    const gebruikerdata = {
      email: email,
      password: password
    }


    this.onNieuwRegister(gebruikerdata).subscribe(
      response => {
        this.inlogService.Nieuwelogin(gebruikerdata)
        // console.log(response);
        this.inlogService.email = email;
        this.inlogService.password = password;
        this.inlogService.onlogin()
        this.isregistered = true;
        this.isRegisterPopupVisible = false;

      },
      error => {
        console.error(error);
      }
    );
  };

  private saveTokenInLocalStorage(token: string){
    localStorage.setItem('authToken', token);
  }
}







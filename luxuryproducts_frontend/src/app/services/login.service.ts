import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Token } from '../models/token';
import { GebruikerDataModel } from '../models/gebruikerData.model';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private httpClient = inject(HttpClient);
  private readonly EMAIL_STORAGE_KEY = 'savedEmail';

  public gebruikerList: any[] = [];
  public admin: boolean = false;
  isloggedin: boolean = false;
  token: string | null = null;


  private _email: string = '';
  private _password: string = '';



  constructor(private router: Router) {
    this.loadTokenFromLocalStorage();
    this.loadEmailFromStorage();

    if (this.token != null) {
      this.isloggedin = true;
      if (this._email == "admin@gmail.com"){
        this.admin = true;
      }
    }
    if(this._email == "admin@gmail.com"){
      this.admin = true;
    }
    if(this._email == "admin@gmail.com"){
      this.admin = true;
    }
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
    this.saveEmailToStorage();
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
    // Intentionally not saving password to storage
  }

  public getToken() {
    return this.token;
  }

  private saveEmailToStorage() {
    if (this._email) {
      localStorage.setItem(this.EMAIL_STORAGE_KEY, this._email);
    } else {
      localStorage.removeItem(this.EMAIL_STORAGE_KEY);
    }
  }

  private loadEmailFromStorage() {
    const savedEmail = localStorage.getItem(this.EMAIL_STORAGE_KEY);
    this._email = savedEmail || '';
    return savedEmail;
  }

  onlogin() {
    this.isloggedin = true;
  }

  getIsloggedin() {
    return this.isloggedin;
  }

  getGebruikerData() {
    return this.gebruikerList;
  }

  voegGebruikerToe(gebruikerName: string, gebruikerEmail: string, gebruikerPassword: string) {
    // Implementation remains the same
  }

  testOfAdmin() {
    if (this.loadEmailFromStorage() === 'admin@gmail.com') {
      this.admin = true;
      return true
    }
    return false;
  }

  print() {
    // console.log(this.gebruikerList);
  }

  getGebruikerList(api: any): Observable<GebruikerDataModel[]> {
    return this.httpClient.get<GebruikerDataModel[]>(api);
  }

  getGebruikerListData() {
  }

  addGebruikerData(gebruikerEmail: string, gebruikerPassword: string) {
    const gebruikerdata = {
      gebruikerName: gebruikerEmail,
      gebruikerEmail: gebruikerEmail,
      gebruikerWachtwoord: gebruikerPassword,
    }

    return this.httpClient.post('http://localhost:8080/api/gebruiker', gebruikerdata);
  }

  Nieuwelogin(loginData: any) {
    return this.httpClient.post<Token>('http://localhost:8080/api/auth/login', loginData).pipe(
      tap(token => {
        if (token.token) {
          this._email = token.email;
          if (this._email === 'admin@mail.com') {
            this.admin = true;
          }
          this.isloggedin = true;
          this.token = token.token;
          this.saveTokenInLocalStorage(token.token);
          this.email = loginData.email; // Save email on successful login
        }
      })
    );
  }

  saveTokenInLocalStorage(token: string) {
    localStorage.setItem('authToken', token);
  }

  private loadTokenFromLocalStorage() {
    this.token = localStorage.getItem('authToken');
  }

  callLogin(): Promise<boolean> {
    return new Promise((resolve) => {
      this.Nieuwelogin({ email: this.email, password: this.password }).subscribe(
        response => {
          // console.log(response);
          resolve(true);
        },
        error => {
          console.error(error);
          resolve(false);
        }
      );
    });
  }

  logout() {
    this.isloggedin = false;
    this.token = null;
    this._password = '';
    localStorage.removeItem('authToken');
    this.email = '';
    localStorage.removeItem(this.EMAIL_STORAGE_KEY);
    this.router.navigate(['/']);
  }

  public getRoleFromToken(): string | null {
    if (!this.token) return null;
    try {
      const payload = JSON.parse(atob(this.token.split('.')[1]));
      return payload.role || null;
    } catch (e) {
      return null;
    }
  }
}

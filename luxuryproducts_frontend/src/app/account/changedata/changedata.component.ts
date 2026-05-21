import { Component, inject } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import {LoginResponse} from '../../models/LoginResponse';
import {TranslatePipe} from "@ngx-translate/core";


@Component({
  selector: 'app-changedata',
    imports: [
        FormsModule,
        TranslatePipe
    ],
  templateUrl: './changedata.component.html',
  styleUrl: './changedata.component.scss'
})
export class ChangedataComponent {
  loginService = inject(LoginService);
  httpClient = inject(HttpClient);
  router = inject(Router);

  nieuwEmail: string = '';
  nieuwWachtwoord: string = '';
  showPassword: boolean = false;
  showNewPassword: boolean = false;

  updateGebruikerByEmail(updatedUser: any) {
    return this.httpClient.put<LoginResponse>(
      `http://localhost:8080/api/users/update/${this.loginService.email}`,
      updatedUser,
      {
        headers: {
          'Authorization': `Bearer ${this.loginService.getToken()}`
        }
      }
    );
  }

  updateRun() {
    const updatedUser = {
      email: this.nieuwEmail,
      password: this.nieuwWachtwoord
    };

    this.updateGebruikerByEmail(this.nieuwEmail).subscribe({
      next: (response: LoginResponse) => {
        console.log("Updated user:", response);

        // Update local storage and service with new token
        this.loginService.token = response.token;
        this.loginService.saveTokenInLocalStorage(response.token);

        // Update email in service if changed
        if (this.nieuwEmail) {
          this.loginService.email = this.nieuwEmail;
        }

        // Clear form
        this.nieuwEmail = '';
        this.nieuwWachtwoord = '';

        // Optional: Redirect or show success message
        this.router.navigate(['/account']);
      },
      error: (err) => {
        console.error("Update failed:", err);
        // Handle error (show message to user)
      }
    });
  }
}

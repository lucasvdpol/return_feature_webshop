import {Component, EventEmitter, inject, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RegisterserviceService} from '../services/registerservice.service';
import {LoginService} from '../services/login.service';
import {WinkelwagenService} from '../services/winkelwagen.service';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-register',
  imports: [
    FormsModule,
    TranslatePipe
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  isRegisterPopupVisible = false;
  @Output() closeRegister = new EventEmitter<void>();
  registerService = inject(RegisterserviceService)
  inlogService = inject(LoginService);
  winkelwagenService = inject(WinkelwagenService);


  registerEmail: string = '';
  registerPassword: string = '';

  onRegister() {
    console.log('close')
    console.log('Register Email:', this.registerEmail);
    console.log('Register Password:', this.registerPassword);
    this.inlogService.addGebruikerData(this.registerEmail,this.registerPassword)
    this.inlogService.email = this.registerEmail;
    this.inlogService.password = this.registerPassword;
    this.inlogService.print()
    this.inlogService.onlogin()
    console.log('close')

    this.registerService.isRegisterPopupVisible = false;
  }

  closeRegisterPopup() {
    this.isRegisterPopupVisible = false;
    this.registerService.isRegisterPopupVisible = false;
    this.closeRegister.emit();
  }



  RoepREgisterAAn(){
    this.registerService.onRegisterData(this.registerEmail,this.registerPassword)
  }

}

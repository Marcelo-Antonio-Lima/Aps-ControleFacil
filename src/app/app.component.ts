import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GoogleAuthService } from './google-auth.service';

@Component({
  selector: 'app-root',
  standalone:true,
  imports: [FormsModule
  ],
  providers: [GoogleAuthService]
  ,
  template: ``
})
export class AppComponent {}

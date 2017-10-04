import { NgModel } from '@angular/forms/src/directives';
import { LoginUser } from '../models/LoginUserModel';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MdDialog, MdDialogRef } from '@angular/material';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {

  userIsLogged = false;
  loginFormVisible = false;
  username;
  password;
  constructor(public dialog: MdDialog, private authService: AuthService) {
  }

  ShowLoginForm() {
    this.loginFormVisible = true;
    const dialogRef = this.dialog.open(DialogResultExampleDialog);
    dialogRef.updatePosition({ top: '80px'});
    

  }
  doLogout(){
    this.authService.logout();
  }
  ngOnInit() {
  }

  

}

@Component({
  selector: 'app-login-dialog',
  styleUrls: ['./login.component.css'],
  template: ` <h2 md-dialog-title color="primary">Prihlásiť</h2>
<md-dialog-content>
<p>
  <md-input-container class="formInputFull">
    <md-icon mdPrefix class="md-darkerIcon inputIcon">person</md-icon>
    <input mdInput placeholder="Username" [(ngModel)]="email" value="">
  </md-input-container>

  <md-input-container class="formInputFull" >
    <md-icon mdPrefix class="md-darkerIcon inputIcon">lock</md-icon>
    <input mdInput placeholder="Password" type="password" [(ngModel)]="password" value="">
  </md-input-container>
  </p>
</md-dialog-content>
<md-dialog-actions class="formActions">
  <!-- Can optionally provide a result for the closing dialog. -->
  <button md-button (click)="RediectToRegister()">Register</button>
  <button md-raised-button (click)="Login()" color="primary">Login</button>
</md-dialog-actions>
`
})
export class DialogResultExampleDialog {

  private email: string;
  private password: string;
  constructor(public dialogRef: MdDialogRef<DialogResultExampleDialog>, private router: Router, private authService: AuthService) {
    
   }
   RediectToRegister(){
     this.dialogRef.close();
      console.log('redirect to home');
  this.router.navigate(['/register'])
  }

  Login(){
    const user: LoginUser = new LoginUser();
    user.Email =  this.email;
    user.Password = this.password;
    this.authService.login(user)
    .subscribe(data => {
      console.log(data);
      this.dialogRef.close();
      this.router.navigate(['/']);
    })
 
  }
 
}

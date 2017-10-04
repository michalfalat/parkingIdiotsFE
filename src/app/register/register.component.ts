import { AuthService } from '../services/auth/auth.service';
import { ApiService } from '../services/api/api.service';
import { Router } from '@angular/router';
import { AbstractControl, FormArray, FormControl, FormGroup, Validator, ValidatorFn, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  waiting = false;
  hasFailed = false;
  errorMessage: String = 'Registration failed!'

  constructor(private router: Router, private apiService: ApiService, private authService: AuthService) { }

  ngOnInit() {
    this.initForm();
  }

  onSubmit() {
    this.waiting = true;
    console.log(this.registerForm);
    this.authService.register(this.registerForm.value).subscribe(
      (data) => {
        this.waiting = false;
      },
      (err) => {
        this.hasFailed = true;
        this.waiting = false;
      });
  }

  onCancel(){
    this.router.navigate(['']);
  }

  private initForm() {
    this.registerForm = new FormGroup({
      'Email': new FormControl (null, [ Validators.required, Validators.email]),
      'Password': new FormControl(null, [Validators.required, Validators.minLength(6), Validators.maxLength(30)]),
      'ConfirmPassword': new FormControl(null, [Validators.required])
    }, 
      this.MatchPassword
    );
  }

   MatchPassword(AC: AbstractControl): ValidatorFn {
       const password = AC.get('Password').value; // to get value in input tag
       const confirmPassword = AC.get('ConfirmPassword').value; // to get value in input tag
        if (password !== confirmPassword) {
            console.log('false');
            AC.get('ConfirmPassword').setErrors( {MatchPassword: true} )
        } else {
            console.log('true');
            return null
        }
    }

  

}

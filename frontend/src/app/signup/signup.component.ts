import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from "@angular/router";

interface ApiResponse {
  status: string;
}

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  firstName = '';
  lastName = '';
  customerId = '';
  pin = '';

  constructor(private http: HttpClient, private router: Router) {}

  submitForm(): void {
    const formData = {
      firstName: this.firstName,
      lastName: this.lastName,
      customerId: this.customerId,
      pin: this.pin
    };

    const apiUrl = 'https://localhost:8080/auth/signup';

    this.http.post<ApiResponse>(apiUrl, formData).subscribe(
      (response) => {
        console.log('Signup successful!', response);

        if (response && response.status === 'success') {
          alert('Signup successful! Welcome to our banking platform.');
          this.router.navigate(['/home']);
        } else {
          console.warn('Unexpected success response:', response);
        }
      },
      (error) => {
        console.error('Error during signup:', error);
      }
    );
  }
}

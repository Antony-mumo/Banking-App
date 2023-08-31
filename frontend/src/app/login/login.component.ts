import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from "@angular/router";

interface ApiResponse {
  status: string;
  token: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  submitForm(): void {
    const formData = {
      username: this.username,
      password: this.password
    };

    const apiUrl = 'https://localhost:8080/auth/login';

    this.http.post<ApiResponse>(apiUrl, formData).subscribe(
      (response) => {
        console.log('Login successful!', response);

        if (response && response.status === 'success') {
          // Store the received token securely (e.g., in localStorage or a cookie)
          localStorage.setItem('authToken', response.token);

          // Redirect to the desired authenticated route
          this.router.navigate(['/home']);
        } else {
          console.warn('Unexpected login response:', response);
        }
      },
      (error) => {
        console.error('Error during login:', error);
      }
    );
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface Transaction {
  id: number;
  amount: number;
  type: string;
  date: string;
  // Other properties as needed
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  customerBalance: number = 0;
  miniStatement: Transaction[] = [];
  // Other properties for forms as needed

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    // Load initial data on component initialization
    this.loadCustomerBalance();
    this.loadMiniStatement();
    // Load other initial data as needed
  }

  loadCustomerBalance(): void {
    const balanceUrl = 'https://localhost:8080/api/customer/balance'; // Replace with your API endpoint
    this.http.get<number>(balanceUrl).subscribe(
      (balance) => {
        this.customerBalance = balance;
      },
      (error) => {
        console.error('Error loading customer balance:', error);
        // Handle error as needed
      }
    );
  }

  loadMiniStatement(): void {
    const miniStatementUrl = 'https://localhost:8080/api/customer/ministatement'; // Replace with your API endpoint
    this.http.get<Transaction[]>(miniStatementUrl).subscribe(
      (transactions) => {
        this.miniStatement = transactions;
      },
      (error) => {
        console.error('Error loading mini statement:', error);
        // Handle error as needed
      }
    );
  }

  performCashDeposit(amount: number): void {
    const depositUrl = 'https://localhost:8080/api/customer/deposit'; // Replace with your API endpoint
    const depositData = { amount: amount };
    this.http.post(depositUrl, depositData).subscribe(
      (response) => {
        console.log('Cash deposit successful!', response);
        this.loadCustomerBalance(); // Refresh customer balance
        this.loadMiniStatement(); // Refresh mini statement
      },
      (error) => {
        console.error('Error during cash deposit:', error);
        // Handle error as needed
      }
    );
  }

  performCashWithdrawal(amount: number): void {
    const withdrawalUrl = 'https://localhost:8080/api/customer/withdraw'; // Replace with your API endpoint
    const withdrawalData = { amount: amount };
    this.http.post(withdrawalUrl, withdrawalData).subscribe(
      (response) => {
        console.log('Cash withdrawal successful!', response);
        this.loadCustomerBalance(); // Refresh customer balance
        this.loadMiniStatement(); // Refresh mini statement
      },
      (error) => {
        console.error('Error during cash withdrawal:', error);
        // Handle error as needed
      }
    );
  }

  performFundsTransfer(recipientAccount: string, amount: number): void {
    const transferUrl = 'https://localhost:8080/api/customer/transfer'; // Replace with your API endpoint
    const transferData = { recipientAccount: recipientAccount, amount: amount };
    this.http.post(transferUrl, transferData).subscribe(
      (response) => {
        console.log('Funds transfer successful!', response);
        this.loadCustomerBalance(); // Refresh customer balance
        this.loadMiniStatement(); // Refresh mini statement
      },
      (error) => {
        console.error('Error during funds transfer:', error);
        // Handle error as needed
      }
    );
  }

  searchTransactions(keyword: string): void {
    const searchUrl = 'https://localhost:8080/api/customer/transactions'; // Replace with your API endpoint
    const searchData = { keyword: keyword };
    this.http.post<Transaction[]>(searchUrl, searchData).subscribe(
      (transactions) => {
        console.log('Transaction search successful!', transactions);
        // Handle the retrieved transactions as needed
      },
      (error) => {
        console.error('Error during transaction search:', error);
        // Handle error as needed
      }
    );
  }
}

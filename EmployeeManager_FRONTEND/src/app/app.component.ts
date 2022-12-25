import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { MatDrawerMode } from '@angular/material/sidenav';
import { Employee } from './model/employee';
import { AuthenticateService } from './service/authenticate.service';
import { EmployeeService } from './service/employee.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  showFiller = false;
  public temp!: Employee;
  public employees?: Employee[];
  public employeesBackup!: Employee[];
  public editEmployee?: Employee;
  public deleteEmployee?: Employee;
  private jwtToken!: string;

  constructor(private employeeService: EmployeeService, private authenticateService: AuthenticateService) { }

  ngOnInit(): void {

    this.authenticate();

    // if (this.isAuthenticated) {
    //   this.getAllEmployees(this.jwtToken);
    // }
  }

  public authenticate(): void {
    this.authenticateService.authenticate().subscribe({
      next: (response: string) => {
        this.jwtToken = response;
        console.log(response);
        this.getAllEmployees(response, this.pageIndex, this.pageSize);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        // alert(error.name+"\n"+error.statusText+"\n"+error.url)
        this.length = 0;
        this.employees = [];
      }
    });
  }

  public getAllEmployees(token: string, pageIndex: number, pageSize: number): void {
    this.employeeService.getAllEmployees(token, pageIndex, pageSize).subscribe({
      next: (response: any) => {
        this.employees = response.content;
        this.employeesBackup = response.content;
        this.length = response.totalElements;
        console.log(this.employees);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    });
  }

  public onAddEmployee(addForm: NgForm): void {
    document.getElementById('closeAddEmployee')?.click();
    // console.log(addForm.value);
    this.employeeService.addEmployee(this.jwtToken, addForm.value).subscribe({
      next: (response: Employee) => {
        console.log(response);
        this.getAllEmployees(this.jwtToken, this.pageIndex, this.pageSize);
        addForm.reset();
      },
      error: (errorResponse: any) => {
        // console.log(errorResponse.error);
        alert(errorResponse.error?.name + '\n' + errorResponse.error?.jobTitle + '\n' + errorResponse.error?.phone + '\n' + errorResponse.error?.email);
        addForm.reset();
      }
    });
  }

  public onUpdateEmployee(employee: Employee): void {
    // console.log(addForm.value);
    this.employeeService.updateEmployee(this.jwtToken, employee).subscribe({
      next: (response: Employee) => {
        console.log(response);
        this.getAllEmployees(this.jwtToken, this.pageIndex, this.pageSize);
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }

  public onDeleteEmployee(employeeId: number): void {
    this.employeeService.deleteEmployee(this.jwtToken, employeeId).subscribe({
      next: (response: void) => {
        console.log(response);
        this.getAllEmployees(this.jwtToken, this.pageIndex, this.pageSize);
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }

  public onSearchEmployee(key: string): void {
    const result: Employee[] = [];
    console.log(key);
    if (key) {
      for (const employee of this.employeesBackup) {
        if (employee.name.toLocaleLowerCase().indexOf(key.toLocaleLowerCase()) !== -1
          || employee.email.toLocaleLowerCase().indexOf(key.toLocaleLowerCase()) !== -1
          || employee.phone.toLocaleLowerCase().indexOf(key.toLocaleLowerCase()) !== -1
          || employee.jobTitle.toLocaleLowerCase().indexOf(key.toLocaleLowerCase()) !== -1) {

          result.push(employee);
        }
      }
      this.employees = result;
    }
    else {
      this.getAllEmployees(this.jwtToken, this.pageIndex, this.pageSize);
    }

  }

  public onOpenModal(employee: Employee, mode: string): void {
    const container = document.getElementById("main-container");
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-bs-toggle', 'modal');

    if (mode === 'add') {
      button.setAttribute('data-bs-target', '#addEmployeeModal');
    }
    if (mode === 'edit') {
      this.editEmployee = employee;
      button.setAttribute('data-bs-target', '#editEmployeeModal');
    }
    if (mode === 'delete') {
      this.deleteEmployee = employee;
      button.setAttribute('data-bs-target', '#deleteEmployeeModal');
    }

    container?.appendChild(button);
    button.click();
  }

  //=====================================================

  length!: number;
  pageSize = 5;
  pageIndex = 0;
  pageSizeOptions = [2, 5, 10, 25];

  showPageSizeOptions = true;

  pageEvent!: PageEvent;

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;

    this.getAllEmployees(this.jwtToken, this.pageIndex, this.pageSize);
  }

  setPageSizeOptions(setPageSizeOptionsInput: string) {
    if (setPageSizeOptionsInput) {
      this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
    }
  }

}

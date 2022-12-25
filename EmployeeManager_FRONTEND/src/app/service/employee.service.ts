import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Employee } from '../model/employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private apiServerURL = environment.apiBaseURL;

  constructor(private http: HttpClient) { }

  public getAllEmployees(token: string, pageIndex: number, pageSize: number) : Observable<Employee[]> {
    token = "Bearer "+token;
    let headers = new HttpHeaders().set("Authorization", token);
    return this.http.get<Employee[]>(`${this.apiServerURL}/employee/all?pageNumber=${pageIndex}&pageSize=${pageSize}`, {headers});
  }

  public addEmployee(token: string, employee : Employee) : Observable<Employee> {
    token = "Bearer "+token;
    let headers = new HttpHeaders().set("Authorization", token);
    return this.http.post<Employee>(`${this.apiServerURL}/employee/add`,employee, {headers});
  }

  public updateEmployee(token: string, employee : Employee) : Observable<Employee> {
    token = "Bearer "+token;
    let headers = new HttpHeaders().set("Authorization", token);
    return this.http.put<Employee>(`${this.apiServerURL}/employee/update`,employee, {headers});
  }
  
  public deleteEmployee(token: string, employeeId : number) : Observable<void> {
    token = "Bearer "+token;
    let headers = new HttpHeaders().set("Authorization", token);
    return this.http.delete<void>(`${this.apiServerURL}/employee/delete/${employeeId}`, {headers});
  }
}

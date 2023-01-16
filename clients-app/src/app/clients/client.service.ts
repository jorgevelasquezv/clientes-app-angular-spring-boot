import { Injectable } from '@angular/core';
import { Client } from './client';

import { Observable, catchError, map, of, tap, throwError } from 'rxjs';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { ResponseClientApp } from './responseClientApp';

@Injectable()
export class ClientService {
  private urlEndPoint: string = 'http://localhost:8080/api/clientes';

  private httpHeader = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient, private router: Router) {}

  getClients(): Observable<Client[]> {
    // return of(CLIENTS);
    // return this.http.get<Client[]>(this.urlEndPoint);
    return this.http.get(this.urlEndPoint).pipe(
      tap((response) => {
        let clients = response as Client[];
        console.log('ClienteService: tap 1');        
        clients.forEach((client) => {
          console.log(client.name);
        });
      }),
      map((response) => {
        let clients = response as Client[];
        return clients.map((client) => {
          client.name = client.name?.toUpperCase();
          return client;
        });
      }),
      tap((response) => {
        console.log('ClienteService: tap 2'); 
        response.forEach((client) => {
          console.log(client.name);
        });
      })
    );
  }

  create(client: Client): Observable<ResponseClientApp> {
    return this.http
      .post<ResponseClientApp>(this.urlEndPoint, client, {
        headers: this.httpHeader,
      })
      .pipe(
        catchError((e) => {
          if (e.status === 400) {
            return throwError(() => e);
          }
          Swal.fire(e.error.mensaje, e.error.error, 'error');
          return throwError(() => e);
        })
      );
  }

  getClient(id: number): Observable<ResponseClientApp> {
    return this.http.get<ResponseClientApp>(`${this.urlEndPoint}/${id}`).pipe(
      catchError((e) => {
        Swal.fire('Error al editar', e.error.mensaje, 'error');
        this.router.navigate(['/clients']);
        return throwError(() => e);
      })
    );
  }

  update(client: Client): Observable<ResponseClientApp> {
    return this.http
      .put<ResponseClientApp>(`${this.urlEndPoint}/${client.id}`, client, {
        headers: this.httpHeader,
      })
      .pipe(
        catchError((e) => {
          if (e.status === 400) {
            return throwError(() => e);
          }
          Swal.fire(e.error.mensaje, e.error.error, 'error');
          return throwError(() => e);
        })
      );
  }

  delete(id: number): Observable<ResponseClientApp> {
    return this.http
      .delete<ResponseClientApp>(`${this.urlEndPoint}/${id}`, {
        headers: this.httpHeader,
      })
      .pipe(
        catchError((e) => {
          Swal.fire(e.error.mensaje, e.error.error, 'error');
          return throwError(() => e);
        })
      );
  }
}

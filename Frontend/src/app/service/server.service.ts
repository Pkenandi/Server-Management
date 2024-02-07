import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {CustomResponse} from "../interface/custom-response";
import {catchError, Observable, tap, throwError} from "rxjs";
import {Server} from "../interface/server";
import {Status} from "../enum/status.enum";

@Injectable({ providedIn: 'root' })
export class ServerService {
  private apiUrl: string = "http://localhost:8080/api";

  constructor(private http: HttpClient) { }

  // @ts-ignore
  servers$ = <Observable<CustomResponse>>this.http.get<CustomResponse>(`${this.apiUrl}/server/list`)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    )

  save$ = (server: Server) =>
    <Observable<CustomResponse>>this.http.post<CustomResponse>(`${this.apiUrl}/server/save`, server)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    )

  ping$ = (ipAddress: string) =>
    <Observable<CustomResponse>>this.http.get<CustomResponse>(`${this.apiUrl}/server/ping/${ipAddress}`)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    )

  filter$ = (status: Status, response: CustomResponse) => <Observable<CustomResponse>>
    new Observable<CustomResponse> ( // Create new Observable
      suscriber => {
        console.log(response);
        suscriber.next( // subscribe to it
          status === Status.ALL ? { ...response, message: `Servers filtered by ${status} status`} : // if the chosen status = ALL
            { // else
              ...response,
              message: response.data.servers // filter the message based on Status found
                .filter(server => server.status === status).length > 0 ? `Servers filtered by ${status === Status.SERVER_UP ? 'SERVER UP' : 'SERVER DOWN'} status` : `No servers of ${status} found`,
              data: {servers: response.data.servers // filter servers where status = the one in params
                  .filter(server => server.status === status)}
            }
        );
        suscriber.complete();
      }
    )
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      )

  delete$ = (serverId: number) =>
    <Observable<CustomResponse>>this.http.delete<CustomResponse>(`${this.apiUrl}/server/delete/${serverId}`)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    )

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error)
    return throwError(`An error occurred - Error code : ${error}`)
  }
}

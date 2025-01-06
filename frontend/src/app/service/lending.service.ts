import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Book} from '../service/book.service';

export enum LendingStatus {
  BOOKED = 'BOOKED',
  DELETED = 'DELETED',
  CANCELLED = 'CANCELLED',
  LENT_OUT = 'LENT_OUT',
  RECEIVED = 'RECEIVED',
  GIVEN_BACK = 'GIVEN_BACK',
  GOT_BACK = 'GOT_BACK'
}

export interface Lending {
  id: number;
  book: Book;
  status: LendingStatus;
  returnDate: string;
  belongsToUser: boolean;
  lentByUser: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class LendingService {
  private readonly apiUrl = 'http://localhost:8080/api/lendings';

  constructor(private http: HttpClient) {
  }

  getLendings(): Observable<Lending[]> {
    return this.http.get<Lending[]>(this.apiUrl);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  lend(id: number): Observable<void> {
    return this.http.post<void>(this.apiUrl, id)
  }

  setLendingStatus(id: number, status: LendingStatus): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/status`, {"id": id, "status": status});
  }

}

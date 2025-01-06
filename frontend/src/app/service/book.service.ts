import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface Book {
  id?: number;
  title: string;
  author: string;
  belongsToUser?: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class BookService {
  private readonly apiUrl = 'http://localhost:8080/api/books';

  constructor(private http: HttpClient) {
  }

  getBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(this.apiUrl, {withCredentials: true});
  }

  search(searchTerm: string): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.apiUrl}/search?searchTerm=${searchTerm}`, {withCredentials: true});
  }

  addBook(book: Book): Observable<Book> {
    return this.http.post<Book>(this.apiUrl, book);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getBookById(id: number): Observable<Book> {
    return this.http.get<Book>(`${this.apiUrl}/${id}`);
  }
}

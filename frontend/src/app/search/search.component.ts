import { Component } from '@angular/core';
import { BookService, Book } from '../service/book.service';
import {FormGroup, FormControl} from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms'; // Import ReactiveFormsModule here
import { HttpClientModule } from '@angular/common/http'; // Import HttpClientModule here
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [ReactiveFormsModule, HttpClientModule],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  books: Book[] = [];
  searchForm = new FormGroup({
    searchTerm: new FormControl(''),
  });

  constructor(private bookService: BookService) {}

  search(): void {
    this.bookService.getBooks().subscribe(
      (data: Book[]) => (this.books = data),
      (error: HttpErrorResponse) => console.error('Error fetching books:', error)
    );
  }
}

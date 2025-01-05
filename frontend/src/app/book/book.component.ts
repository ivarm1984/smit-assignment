import { Component } from '@angular/core';
import { BookService, Book } from '../service/book.service';
import { HttpClientModule, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-book',
  imports: [HttpClientModule],
  templateUrl: './book.component.html',
  styleUrl: './book.component.css'
})
export class BookComponent {
  bookId!: number;
  book!: Book;

  constructor(private route: ActivatedRoute, private bookService: BookService) {}

  ngOnInit(): void {
    this.bookId = Number(this.route.snapshot.paramMap.get('id')!);
    this.bookService.getBookById(this.bookId).subscribe(
      (data: Book) => (this.book = data),
      (error: HttpErrorResponse) => console.error('Error fetching book:', error)
    );
  }
}

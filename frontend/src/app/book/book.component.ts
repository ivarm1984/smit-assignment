import { Component } from '@angular/core';
import { BookService, Book } from '../service/book.service';
import { LendingService, Lending } from '../service/lending.service';
import { HttpClientModule, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-book',
  imports: [HttpClientModule],
  templateUrl: './book.component.html',
  styleUrl: './book.component.css'
})
export class BookComponent {
  bookId!: number;
  book!: Book;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private lendingService: LendingService,
    private bookService: BookService) {}

  ngOnInit(): void {
    this.bookId = Number(this.route.snapshot.paramMap.get('id')!);
    this.bookService.getBookById(this.bookId).subscribe(
      (data: Book) => (this.book = data),
      (error: HttpErrorResponse) => console.error('Error fetching book:', error)
    );
  }

  lendBook() {
    this.lendingService.lend(this.book.id!).subscribe({
     next: () => {
       this.router.navigate(['lendings']);
     },
     error: (err) => {
       console.error(`Failed to delete book with id ${this.book.id}`, err);
     }
   });
  }

  deleteBook() {
    this.bookService.delete(this.book.id!).subscribe({
     next: () => {
       console.log(`Book with id ${this.book.id} deleted successfully`);
       this.router.navigate(['']);
     },
     error: (err) => {
       console.error(`Failed to delete book with id ${this.book.id}`, err);
     }
   });
  }
}

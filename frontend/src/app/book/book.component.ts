import {Component} from '@angular/core';
import {Book, BookService} from '../service/book.service';
import {LendingService} from '../service/lending.service';
import {HttpClientModule, HttpErrorResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

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
              private bookService: BookService,
              private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.bookId = Number(this.route.snapshot.paramMap.get('id')!);
    this.bookService.getBookById(this.bookId).subscribe(
      (data: Book) => (this.book = data),
      (error: HttpErrorResponse) => this.toastr.error(`Error fetching book: ${error}`)
    );
  }

  lendBook() {
    this.lendingService.lend(this.book.id!).subscribe({
      next: () => {
        this.toastr.success('Book lending successful!');
        this.router.navigate(['lendings']);
      },
      error: (err) => {
        this.toastr.error(`Failed to delete book with id ${this.book.id}`);
      }
    });
  }

  deleteBook() {
    this.bookService.delete(this.book.id!).subscribe({
      next: () => {
        console.log(`Book with id ${this.book.id!} deleted successfully`);
        this.router.navigate(['']);
      },
      error: (err) => {
        this.toastr.error(`Failed to delete book with id ${this.book.id!}`);
      }
    });
  }
}

import {Component} from '@angular/core';
import {Book, BookService} from '../service/book.service';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {HttpClientModule, HttpErrorResponse} from '@angular/common/http';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';

@Component({
  selector: 'app-book-create',
  imports: [ReactiveFormsModule, HttpClientModule],
  templateUrl: './book-create.component.html',
  styleUrl: './book-create.component.css'
})
export class BookCreateComponent {
  createForm = new FormGroup({
    title: new FormControl<string>('', [Validators.required]),
    author: new FormControl<string>('', [Validators.required]),
  });

  constructor(private bookService: BookService,
              private router: Router,
              private toastr: ToastrService) {
  }

  create(): void {

    if (this.createForm.valid) {
      const book: Book = {
        title: this.createForm.get('title')!.value!,
        author: this.createForm.get('author')!.value!
      };

      this.bookService.addBook(book).subscribe({
        next: () => {
          this.toastr.success('Book added');
          this.router.navigate(['']);
        },
        error: (error: HttpErrorResponse) => this.toastr.error('Failed to create book ${error}'),
      });
    } else {
      this.toastr.error('Form invalid');
    }
  }
}

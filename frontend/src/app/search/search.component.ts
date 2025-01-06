import {Component} from '@angular/core';
import {BookService, Book} from '../service/book.service';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {HttpErrorResponse} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule, HttpClient} from '@angular/common/http';
import {RouterLink} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [ReactiveFormsModule, HttpClientModule, RouterLink],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  books: Book[] = [];
  searchForm = new FormGroup({
    searchTerm: new FormControl<string>('', [Validators.required]),
  });

  constructor(private bookService: BookService, private toastr: ToastrService) {
  }

  search(): void {
    this.bookService.search(this.searchForm.get('searchTerm')!.value!).subscribe(
      (data: Book[]) => (this.books = data),
      (error: HttpErrorResponse) => this.toastr.error('Error fetching books')
    );
  }
}

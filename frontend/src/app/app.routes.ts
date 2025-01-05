import { Routes } from '@angular/router';
import {SearchComponent} from './search/search.component';
import {BookActivityComponent} from './book-activity/book-activity.component';
import {BookComponent} from './book/book.component';
import {BookCreateComponent} from './book-create/book-create.component';

export const routes: Routes = [
    {
      path: '',
      title: 'Search',
      component: SearchComponent,
    },
    {
      path: 'book/:id',
      title: 'Book',
      component: BookComponent,
    },
  {
        path: 'book-create',
        title: 'Create a new book',
        component: BookCreateComponent
      },
    {
      path: 'book-activity',
      title: 'Book Activity',
      component: BookActivityComponent
    },];

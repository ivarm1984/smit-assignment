import { Routes } from '@angular/router';
import {SearchComponent} from './search/search.component';
import {BookActivityComponent} from './book-activity/book-activity.component';



export const routes: Routes = [
    {
      path: '',
      title: 'Search',
      component: SearchComponent,
    },
    {
      path: 'book-activity',
      title: 'Book Activity',
      component: BookActivityComponent,
    },];

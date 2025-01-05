import { Component, OnInit } from '@angular/core';
import { LendingService, Lending, LendingStatus } from '../service/lending.service';
import { BookService, Book } from '../service/book.service';
import { HttpClientModule, HttpClient, HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-book-activity',
  imports: [],
  templateUrl: './book-activity.component.html',
  styleUrl: './book-activity.component.css'
})
export class BookActivityComponent implements OnInit {
  lendings: Lending[] = [];
  LendingStatus = LendingStatus;

  constructor(
    private lendingService: LendingService,
    private bookService: BookService) {}

  ngOnInit(): void {
    this.getLendings();
  }

  getLendings(): void {
    this.lendingService.getLendings().subscribe(
      (lendings: Lending[]) => {
        this.lendings = lendings;
      },
      (error) => {
        console.error('Error fetching lendings:', error);
      }
    );
  }

  canDelete(lendingId: number): boolean {
    const lending = this.lendings.find((lending: Lending) => lending.id === lendingId)!;
    return lending.belongsToUser && lending.status === LendingStatus.BOOKED;
  }

  canCancel(lendingId: number): boolean {
    const lending = this.lendings.find((lending: Lending) => lending.id === lendingId)!;
    return lending.lentByUser && lending.status === LendingStatus.BOOKED;
  }

  cenSetToLent(lendingId: number): boolean {
    const lending = this.lendings.find((lending: Lending) => lending.id === lendingId)!;
    return lending.belongsToUser && lending.status === LendingStatus.BOOKED;
  }

  canSetToReceived(lendingId: number): boolean {
    const lending = this.lendings.find((lending: Lending) => lending.id === lendingId)!;
    return lending.lentByUser && lending.status === LendingStatus.LENT_OUT;
  }

  canSetToGivenBack(lendingId: number): boolean {
    const lending = this.lendings.find((lending: Lending) => lending.id === lendingId)!;
    return lending.lentByUser && lending.status === LendingStatus.RECEIVED;
  }

  canSetToGotBack(lendingId: number): boolean {
    const lending = this.lendings.find((lending: Lending) => lending.id === lendingId)!;
    return lending.belongsToUser && lending.status === LendingStatus.GIVEN_BACK;
  }

  updateStatus(lendingId: number, status: LendingStatus) {
    console.log('in update status');
      this.lendingService.setLendingStatus(lendingId, status).subscribe(
       () => {
         console.log("success??")
         this.getLendings();
       },
       (err: HttpErrorResponse) => {
         console.log(`Failed`, err);
       }
     );
   }
}

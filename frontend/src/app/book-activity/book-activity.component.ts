import {Component, OnInit} from '@angular/core';
import {Lending, LendingService, LendingStatus} from '../service/lending.service';
import {BookService} from '../service/book.service';
import {HttpErrorResponse} from '@angular/common/http';
import {ToastrService} from 'ngx-toastr';

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
    private bookService: BookService,
    private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.getLendings();
  }

  getLendings(): void {
    this.lendingService.getLendings().subscribe(
      (lendings: Lending[]) => {
        this.lendings = lendings;
      },
      (error) => {
        this.toastr.error('Error fetching lendings');
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
    this.lendingService.setLendingStatus(lendingId, status).subscribe(
      () => {
        this.toastr.success('Book status updated');
        this.getLendings();
      },
      (err: HttpErrorResponse) => {
        this.toastr.error('Error fetching lendings');
      }
    );
  }
}

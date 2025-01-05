package ee.ivar.smit.proovitoo.lending;

import ee.ivar.smit.proovitoo.book.BookResource;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LendingResource {
    private Long id;

    private BookResource book;

    private LendingStatus status;

    private LocalDate returnDate;

    private boolean belongsToUser;
    private boolean lentByUser;
}

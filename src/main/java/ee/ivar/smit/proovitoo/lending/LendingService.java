package ee.ivar.smit.proovitoo.lending;

import ee.ivar.smit.proovitoo.book.BookEntity;
import ee.ivar.smit.proovitoo.book.BookRepository;
import ee.ivar.smit.proovitoo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static ee.ivar.smit.proovitoo.lending.LendingStatus.CANCELLED;
import static ee.ivar.smit.proovitoo.lending.LendingStatus.DELETED;
import static ee.ivar.smit.proovitoo.lending.LendingStatus.GOT_BACK;

@Service
@RequiredArgsConstructor
public class LendingService {

    public static final int DEFAULT_LENDING_IN_DAYS = 40;
    public static final List<LendingStatus> FINAL_STATUSES = List.of(CANCELLED, DELETED, GOT_BACK);

    private final UserService userService;
    private final LendingRepository lendingRepository;
    private final BookRepository bookRepository;
    private final LendingUpdateValidator lendingUpdateValidator;

    public List<LendingEntity> getLendings() {
        return lendingRepository.findAllByOwnerOrLender(userService.getCurrentUser().getId(), FINAL_STATUSES);
    }

    public LendingEntity lend(Long bookId) {
        BookEntity book = bookRepository.getReferenceById(bookId);
        if (userService.isCurrentUser(book.getUser())) {
            throw new LendingException("Cannot lend your own book!");
        }

        LendingEntity lending = new LendingEntity();
        lending.setBook(book);
        lending.setUser(userService.getCurrentUser());
        lending.setStatus(LendingStatus.BOOKED);
        lending.setReturnDate(LocalDate.now().plusDays(DEFAULT_LENDING_IN_DAYS));
        return lendingRepository.save(lending);
    }

    public void updateStatus(LendingStatusUpdateRequest request) {
        LendingEntity lending = lendingRepository.getReferenceById(request.id());
        if (!lendingUpdateValidator.isValidUpdate(request, lending)) {
            throw new LendingException("Status cannot be update!");
        }
        lending.setStatus(request.status());
        lendingRepository.save(lending);
    }
}

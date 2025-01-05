package ee.ivar.smit.proovitoo.lending;

import ee.ivar.smit.proovitoo.user.UserEntity;
import ee.ivar.smit.proovitoo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static ee.ivar.smit.proovitoo.lending.LendingStatus.BOOKED;
import static ee.ivar.smit.proovitoo.lending.LendingStatus.CANCELLED;
import static ee.ivar.smit.proovitoo.lending.LendingStatus.DELETED;
import static ee.ivar.smit.proovitoo.lending.LendingStatus.GIVEN_BACK;
import static ee.ivar.smit.proovitoo.lending.LendingStatus.GOT_BACK;
import static ee.ivar.smit.proovitoo.lending.LendingStatus.LENT_OUT;
import static ee.ivar.smit.proovitoo.lending.LendingStatus.RECEIVED;

@Component
@RequiredArgsConstructor
public class LendingUpdateValidator {

    private static final Map<LendingStatus, List<LendingStatus>> VALID_CHANGES = Map.of(
            BOOKED, List.of(CANCELLED, DELETED, LENT_OUT),
            LENT_OUT, List.of(RECEIVED),
            RECEIVED, List.of(GIVEN_BACK),
            GIVEN_BACK, List.of(GOT_BACK)
    );

    private final UserService userService;

    public boolean isValidUpdate(LendingStatusUpdateRequest request, LendingEntity lending) {
        if (!isValidUpdatePath(lending.getStatus(), request.status())) {
            return false;
        }
        UserEntity currentUser = userService.getCurrentUser();
        UserEntity lender = lending.getUser();
        UserEntity owner = lending.getBook().getUser();

        if (request.status() == CANCELLED && currentUser.getId().equals(lender.getId())) {
            return true;
        }
        if (request.status() == DELETED && currentUser.getId().equals(owner.getId())) {
            return true;
        }
        if (request.status() == LENT_OUT && currentUser.getId().equals(owner.getId())) {
            return true;
        }
        if (request.status() == RECEIVED && currentUser.getId().equals(lender.getId())) {
            return true;
        }
        if (request.status() == GIVEN_BACK && currentUser.getId().equals(lender.getId())) {
            return true;
        }
        if (request.status() == GOT_BACK && currentUser.getId().equals(owner.getId())) {
            return true;
        }
        return false;
    }

    private boolean isValidUpdatePath(LendingStatus from, LendingStatus to) {
        if (VALID_CHANGES.containsKey(from)) {
            return VALID_CHANGES.get(from).contains(to);
        }
        return false;
    }
}

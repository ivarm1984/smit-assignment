package ee.ivar.smit.proovitoo.lending

import ee.ivar.smit.proovitoo.book.BookEntity
import ee.ivar.smit.proovitoo.user.UserEntity
import ee.ivar.smit.proovitoo.user.UserService
import spock.lang.Specification

import static ee.ivar.smit.proovitoo.lending.LendingStatus.BOOKED
import static ee.ivar.smit.proovitoo.lending.LendingStatus.DELETED
import static ee.ivar.smit.proovitoo.lending.LendingStatus.CANCELLED
import static ee.ivar.smit.proovitoo.lending.LendingStatus.LENT_OUT
import static ee.ivar.smit.proovitoo.lending.LendingStatus.RECEIVED
import static ee.ivar.smit.proovitoo.lending.LendingStatus.GIVEN_BACK
import static ee.ivar.smit.proovitoo.lending.LendingStatus.GOT_BACK


class LendingUpdateValidatorTest extends Specification {

    private userService = Mock UserService

    private lendingUpdateValidator = new LendingUpdateValidator(userService)

    def "should be final status"() {
        given:
        def lending = new LendingEntity(user: new UserEntity(id: 1),
                status: from,
                book: new BookEntity(user: new UserEntity(id: 1)))
        userService.currentUser >> new UserEntity(id: 1)

        when:
        def result = !Arrays.stream(LendingStatus.values())
                .map { lendingUpdateValidator.isValidUpdate(new LendingStatusUpdateRequest(1, it), lending) }
                .anyMatch { it == true }

        then:
        result == isFinal

        where:
        from       | isFinal
        BOOKED     | false
        DELETED    | true
        CANCELLED  | true
        LENT_OUT   | false
        RECEIVED   | false
        GIVEN_BACK | false
        GOT_BACK   | true
    }

    def "should allow valid status changes"() {
        given:
        def request = new LendingStatusUpdateRequest(1, to)
        def lending = new LendingEntity(user: new UserEntity(id: 1),
                status: from,
                book: new BookEntity(user: new UserEntity(id: 1)))
        userService.currentUser >> new UserEntity(id: 1)

        when:
        def result = lendingUpdateValidator.isValidUpdate(request, lending)

        then:
        result == expectedResult

        where:
        from       | to         | expectedResult
        BOOKED     | DELETED    | true
        BOOKED     | CANCELLED  | true
        BOOKED     | LENT_OUT   | true
        BOOKED     | RECEIVED   | false
        BOOKED     | GIVEN_BACK | false
        BOOKED     | GOT_BACK   | false
        LENT_OUT   | BOOKED     | false
        LENT_OUT   | CANCELLED  | false
        LENT_OUT   | LENT_OUT   | false
        LENT_OUT   | RECEIVED   | true
        LENT_OUT   | GIVEN_BACK | false
        LENT_OUT   | GOT_BACK   | false
        RECEIVED   | BOOKED     | false
        RECEIVED   | CANCELLED  | false
        RECEIVED   | LENT_OUT   | false
        RECEIVED   | RECEIVED   | false
        RECEIVED   | GIVEN_BACK | true
        RECEIVED   | GOT_BACK   | false
        GIVEN_BACK | BOOKED     | false
        GIVEN_BACK | CANCELLED  | false
        GIVEN_BACK | LENT_OUT   | false
        GIVEN_BACK | RECEIVED   | false
        GIVEN_BACK | GIVEN_BACK | false
        GIVEN_BACK | GOT_BACK   | true
    }
}
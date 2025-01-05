package ee.ivar.smit.proovitoo.lending

import ee.ivar.smit.proovitoo.book.BookEntity
import ee.ivar.smit.proovitoo.book.BookRepository
import ee.ivar.smit.proovitoo.user.UserEntity
import ee.ivar.smit.proovitoo.user.UserService
import spock.lang.Specification

class LendingServiceTest extends Specification {

    private userService = Mock(UserService)
    private lendingRepository = Mock(LendingRepository)
    private bookRepository = Mock(BookRepository)
    private lendingUpdateValidator = Mock(LendingUpdateValidator)


    private lendingService = new LendingService(userService, lendingRepository,
            bookRepository, lendingUpdateValidator)

    def "should forbid lending own book" () {
        given:
        userService.isCurrentUser(_) >> true
        bookRepository.getReferenceById(1L) >> new BookEntity()

        when:
        lendingService.lend(1L)

        then:
        def e = thrown LendingException
        e.message == "Cannot lend your own book!"
    }

    def "should create lending" () {
        given:
        userService.isCurrentUser(_) >> false
        bookRepository.getReferenceById(1L) >> new BookEntity()
        userService.getCurrentUser() >> new UserEntity(id: 123)

        when:
        lendingService.lend(1L)

        then:
        1 * lendingRepository.save((LendingEntity lending) -> {
            lending.status == LendingStatus.BOOKED
            lending.user.id == 123L
        })
    }

    def "should validate update status request" () {
        given:
        def request = new LendingStatusUpdateRequest(1L, LendingStatus.CANCELLED)
        lendingRepository.getReferenceById(1L) >> new LendingEntity()

        when:
        lendingService.updateStatus(request)

        then:
        1 * lendingUpdateValidator.isValidUpdate(_, _) >> true
        1 * lendingRepository.save(_)
    }

    def "should not save invalid lending" () {
        given:
        def request = new LendingStatusUpdateRequest(1L, LendingStatus.CANCELLED)
        lendingRepository.getReferenceById(1L) >> new LendingEntity()
        lendingUpdateValidator.isValidUpdate(_, _) >> false

        when:
        lendingService.updateStatus(request)

        then:
        0 * lendingRepository.save(_)
        thrown LendingException
    }
}

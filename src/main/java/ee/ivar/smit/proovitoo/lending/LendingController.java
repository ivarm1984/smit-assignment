package ee.ivar.smit.proovitoo.lending;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lendings")
@RequiredArgsConstructor
@Log4j2
public class LendingController {

    private final LendingService lendingService;
    private final LendingConverter lendingConverter;

    @GetMapping
    public List<LendingResource> getLendings() {
        log.info("Getting lendings");
        return lendingService.getLendings().stream().map(lendingConverter::convertToResource).toList();
    }

    @PostMapping
    public LendingResource createLending(@RequestBody Long bookId) {
        log.info("Creating lending {}", bookId);
        return lendingConverter.convertToResource(lendingService.lend(bookId));
    }

    @PostMapping("/status")
    public void updateLendingStatus(@RequestBody LendingStatusUpdateRequest request) {
        log.info("Updating book {} status to {}", request.id(), request.status());
        lendingService.updateStatus(request);
    }
}

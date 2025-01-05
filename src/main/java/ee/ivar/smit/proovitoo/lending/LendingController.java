package ee.ivar.smit.proovitoo.lending;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lendings")
@RequiredArgsConstructor
public class LendingController {

    private final LendingService lendingService;
    private final LendingConverter lendingConverter;

    @GetMapping
    public List<LendingResource> getLendings() {
        return lendingService.getLendings().stream().map(lendingConverter::convertToResource).toList();
    }

    @PostMapping
    public LendingResource createLending(@RequestBody Long bookId) {
        return lendingConverter.convertToResource(lendingService.lend(bookId));
    }

    @PostMapping("/status")
    public void updateLendingStatus(@RequestBody LendingStatusUpdateRequest request) {
        lendingService.updateStatus(request);
    }
}

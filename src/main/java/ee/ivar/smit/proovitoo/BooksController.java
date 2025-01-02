package ee.ivar.smit.proovitoo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {

    private final BookRepository bookRepository;
    private final BookConverter bookConverter;

    @GetMapping
    public List<BookResource> getBooks() {
        return bookRepository.findAll().stream().map(bookConverter::convertToDto).toList();
    }
}

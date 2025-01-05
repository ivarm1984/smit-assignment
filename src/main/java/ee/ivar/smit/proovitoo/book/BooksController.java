package ee.ivar.smit.proovitoo.book;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BooksController {

    private final BookRepository bookRepository;
    private final BookConverter bookConverter;

    @GetMapping
    public List<BookResource> getBooks() {
        return toResources(bookRepository.findAll());
    }

    @GetMapping("/{bookId}")
    public BookResource getBook(@PathVariable Long bookId) {
        return toResource(bookRepository.getReferenceById(bookId));
    }

    @GetMapping("/search")
    public List<BookResource> searchBooks(@RequestParam String searchTerm) {
        return toResources(bookRepository.searchBooks(searchTerm));
    }

    private List<BookResource> toResources(List<BookEntity> books) {
        return books.stream().map(bookConverter::convertToResource).toList();
    }

    private BookResource toResource(BookEntity book) {
        return bookConverter.convertToResource(book);
    }


}

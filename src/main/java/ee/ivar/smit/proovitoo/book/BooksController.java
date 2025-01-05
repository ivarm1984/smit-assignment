package ee.ivar.smit.proovitoo.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Log4j2
public class BooksController {

    private final BookRepository bookRepository;
    private final BookConverter bookConverter;
    private final BookService bookService;

    @GetMapping("/{bookId}")
    public BookResource getBook(@PathVariable Long bookId) {
        log.info("Getting book {}", bookId);
        return toResource(bookRepository.getReferenceById(bookId));
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable Long bookId) {
        log.info("Deleting book {}", bookId);
        bookService.delete(bookId);
    }

    @PostMapping
    public BookResource addBook(@RequestBody  BookResource book) {
        log.info("Adding book {} - {}", book.getTitle(), book.getAuthor());
        return toResource(bookService.addBook(book));
    }

    @GetMapping("/search")
    public List<BookResource> searchBooks(@RequestParam String searchTerm) {
        log.info("Performing book search {}", searchTerm);
        return toResources(bookRepository.searchBooks(searchTerm));
    }

    private List<BookResource> toResources(List<BookEntity> books) {
        return books.stream().map(bookConverter::convertToResource).toList();
    }

    private BookResource toResource(BookEntity book) {
        return bookConverter.convertToResource(book);
    }


}

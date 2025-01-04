package ee.ivar.smit.proovitoo.book;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        return bookRepository.findAll().stream().map(bookConverter::convertToDto).toList();
    }

    @PostConstruct
    public void post() {
        if (bookRepository.findAll().isEmpty()) {
            BookEntity book1 = new BookEntity();
            book1.setAuthor("author1");
            book1.setTitle("title");
            bookRepository.save(book1);
            BookEntity book2 = new BookEntity();
            book2.setAuthor("author2");
            book2.setTitle("title asdfadsf");
            bookRepository.save(book2);
        }
    }
}

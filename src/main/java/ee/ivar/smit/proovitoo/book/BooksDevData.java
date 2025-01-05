package ee.ivar.smit.proovitoo.book;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev-data")
@Component
@RequiredArgsConstructor
public class BooksDevData {

    private final BookRepository bookRepository;

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

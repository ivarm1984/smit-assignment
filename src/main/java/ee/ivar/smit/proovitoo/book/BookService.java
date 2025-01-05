package ee.ivar.smit.proovitoo.book;

import ee.ivar.smit.proovitoo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserService userService;
    private final BookConverter bookConverter;

    public void delete(Long id) {
        BookEntity book = bookRepository.getReferenceById(id);
        if (userService.isCurrentUser(book.getUser())) {
            bookRepository.delete(book);
        } else {
            throw new BookDeleteException();
        }
    }

    public BookEntity addBook(BookResource book) {
        BookEntity bookEntity = bookConverter.convertToEntity(book);
        bookEntity.setUser(userService.getCurrentUser());
        bookRepository.save(bookEntity);
        return bookEntity;
    }
}

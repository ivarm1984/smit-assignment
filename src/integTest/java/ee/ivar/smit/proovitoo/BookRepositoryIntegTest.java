package ee.ivar.smit.proovitoo;

import ee.ivar.smit.proovitoo.book.BookEntity;
import ee.ivar.smit.proovitoo.book.BookRepository;
import ee.ivar.smit.proovitoo.user.UserEntity;
import ee.ivar.smit.proovitoo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BookRepositoryIntegTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    void beforeEach() {
        user = new UserEntity();
        user.setSub("123");
        userRepository.save(user);
    }

    @Test
    void shouldFindBooksByTitle() {
        // given
        insertBook("Old story", "lastname firstname");
        insertBook("new story", "lastname firstname");

        // when
        List<BookEntity> books = bookRepository.searchBooks("old");

        // then
        assertThat(books).hasSize(1);
    }

    @Test
    void shouldFindByAuthor() {
        // given
        insertBook("Old story", "lastname firstname");
        insertBook("new story", "lastname firstname");

        // when
        List<BookEntity> books = bookRepository.searchBooks("first");

        // then
        assertThat(books).hasSize(2);
    }

    private void insertBook(String title, String author) {
        bookRepository.save(BookEntity.builder()
                .author(author)
                .title(title)
                .user(user)
                .build());
    }
}

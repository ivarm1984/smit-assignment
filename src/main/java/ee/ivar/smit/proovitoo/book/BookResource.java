package ee.ivar.smit.proovitoo.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResource {
    private Long id;
    private String title;
    private String author;
    private boolean belongsToUser;
}

package ee.ivar.smit.proovitoo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookResource {
    private Long id;
    private String title;
    private String author;
}

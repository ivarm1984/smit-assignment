package ee.ivar.smit.proovitoo.book;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookConverter {

    private final ModelMapper modelMapper;

    public BookResource convertToDto(BookEntity entity) {
        return modelMapper.map(entity, BookResource.class);

    }

    public BookEntity convertToEntity(BookResource resource) {
        return modelMapper.map(resource, BookEntity.class);
    }
}

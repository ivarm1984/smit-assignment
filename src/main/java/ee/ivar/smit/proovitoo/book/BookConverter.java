package ee.ivar.smit.proovitoo.book;

import ee.ivar.smit.proovitoo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookConverter {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public BookResource convertToResource(BookEntity entity) {
        BookResource resource = modelMapper.map(entity, BookResource.class);

        if (entity.getUser().getId().equals(userService.getCurrentUser().getId())) {
            resource.setBelongsToUser(true);
        }
        return resource;
    }

    public BookEntity convertToEntity(BookResource resource) {
        return modelMapper.map(resource, BookEntity.class);
    }
}

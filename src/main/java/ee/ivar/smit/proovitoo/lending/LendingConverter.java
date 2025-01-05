package ee.ivar.smit.proovitoo.lending;

import ee.ivar.smit.proovitoo.book.BookConverter;
import ee.ivar.smit.proovitoo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LendingConverter {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final BookConverter bookConverter;

    public LendingResource convertToResource(LendingEntity entity) {
        LendingResource resource = modelMapper.map(entity, LendingResource.class);

        if (userService.isCurrentUser(entity.getUser())) {
            resource.setLentByUser(true);
        }

        if (userService.isCurrentUser(entity.getBook().getUser())) {
            resource.setBelongsToUser(true);
        }
        resource.setBook(bookConverter.convertToResource(entity.getBook()));
        return resource;
    }

    public LendingEntity convertToEntity(LendingResource resource) {
        return modelMapper.map(resource, LendingEntity.class);
    }

}
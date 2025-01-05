package ee.ivar.smit.proovitoo.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserEntity getCurrentUser() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String sub = jwt.getClaim("sub");

        return getUserBySub(sub);
    }

    @Transactional
    public UserEntity getUserBySub(String sub) {
        var user = userRepository.findBySub(sub);
        if (user.isPresent()) {
            return user.get();
        }

        UserEntity newUser = new UserEntity();
        newUser.setSub(sub);
        userRepository.save(newUser);

        return newUser;
    }

    public boolean isCurrentUser(UserEntity user) {
        return getCurrentUser().getId().equals(user.getId());
    }
}

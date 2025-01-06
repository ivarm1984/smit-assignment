package ee.ivar.smit.proovitoo.lending;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LendingRepository extends JpaRepository<LendingEntity, Long> {

    @Query("SELECT l FROM LendingEntity l " +
            "JOIN l.book b " +
            "WHERE (b.user.id = :userId " +
            "OR l.user.id = :userId) " +
            "AND l.status NOT IN (:excludedStatuses)")
    List<LendingEntity> findAllByOwnerOrLender(Long userId, List<LendingStatus> excludedStatuses);
}

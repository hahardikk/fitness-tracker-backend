package tracker.repository;

import tracker.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {
    List<Activity> findByUserId(String userId);

    @Query("""
                    SELECT a.type, COUNT(a)
                            FROM Activity a
                            WHERE a.user.id = :userId
                            GROUP BY a.type
            """)
    List<Object[]> getActivityDistribution(@Param("userId") String userId);

    @Query("""
                SELECT a
                FROM Activity a
                WHERE a.user.id = :userId
                ORDER BY a.startTime DESC
            """)
    List<Activity> findByUserIdByStartTimeDesc(String userId);
}

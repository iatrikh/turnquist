package learning.springboot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

    // @Query("select v from VideoEntity v where v.name = ?1")
    // List<VideoEntity> findCustomerReport(String name);

    List<VideoEntity> findByNameContainsOrDescriptionContainsAllIgnoreCase(String name, String description);

    List<VideoEntity> findByNameContainsIgnoreCase(String name);

    List<VideoEntity> findByDescriptionContainsIgnoreCase(String description);

    @Override
    @PreAuthorize("#entity.username == authentication.name || authentication.name == 'admin'")
    void delete(VideoEntity entity);
}

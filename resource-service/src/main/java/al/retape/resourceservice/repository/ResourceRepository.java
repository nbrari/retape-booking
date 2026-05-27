package al.retape.resourceservice.repository;

import al.retape.resourceservice.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByAvailableSlotsGreaterThan(Integer slots);
}
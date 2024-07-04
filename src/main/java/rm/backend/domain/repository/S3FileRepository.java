package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.S3File;

public interface S3FileRepository extends JpaRepository<S3File, Long> {
}

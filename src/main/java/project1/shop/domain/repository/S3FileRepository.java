package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.S3File;

public interface S3FileRepository extends JpaRepository<S3File, Long> {
}

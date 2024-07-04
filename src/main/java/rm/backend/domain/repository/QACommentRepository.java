package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.QAComment;

import java.util.Optional;

public interface QACommentRepository extends JpaRepository<QAComment, Long> {

    Optional<QAComment> findByItemQA_ItemQAId(Long itemQAId);
}

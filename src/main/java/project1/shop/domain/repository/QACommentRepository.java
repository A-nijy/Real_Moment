package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.QAComment;

public interface QACommentRepository extends JpaRepository<QAComment, Long> {
}

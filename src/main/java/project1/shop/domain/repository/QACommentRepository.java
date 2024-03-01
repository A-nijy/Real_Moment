package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.ItemQA;
import project1.shop.domain.entity.QAComment;
import project1.shop.domain.entity.QQAComment;

import java.util.Optional;

public interface QACommentRepository extends JpaRepository<QAComment, Long> {

    Optional<QAComment> findByItemQA_ItemQAId(Long itemQAId);
}

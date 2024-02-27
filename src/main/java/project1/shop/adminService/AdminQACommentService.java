package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Admin;
import project1.shop.domain.entity.ItemQA;
import project1.shop.domain.entity.QAComment;
import project1.shop.domain.repository.AdminRepository;
import project1.shop.domain.repository.ItemQARepository;
import project1.shop.domain.repository.QACommentRepository;
import project1.shop.dto.innerDto.QACommentDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminQACommentService {

    private final QACommentRepository qaCommentRepository;
    private final AdminRepository adminRepository;
    private final ItemQARepository itemQARepository;

    @Transactional
    public void saveQAComment(Long id, QACommentDto.SaveRequest request) {

        Admin admin = adminRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        ItemQA itemQA = itemQARepository.findById(request.getItemQAId()).orElseThrow(IllegalArgumentException::new);

        QAComment qaComment = new QAComment(admin, itemQA, request);

        qaCommentRepository.save(qaComment);

    }


    @Transactional
    public void updateQAComment(Long id, QACommentDto.UpdateRequest request) {

        Admin admin = adminRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        QAComment qaComment = qaCommentRepository.findById(request.getQaCommentId()).orElseThrow(IllegalArgumentException::new);

        qaComment.update(admin, request);
    }

    @Transactional
    public void deleteQAComment(Long qaCommentId) {

        QAComment qaComment = qaCommentRepository.findById(qaCommentId).orElseThrow(IllegalArgumentException::new);

        qaCommentRepository.delete(qaComment);
    }
}

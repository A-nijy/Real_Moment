package rm.backend.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.Admin;
import rm.backend.domain.entity.ItemQA;
import rm.backend.domain.entity.QAComment;
import rm.backend.domain.repository.AdminRepository;
import rm.backend.domain.repository.ItemQARepository;
import rm.backend.domain.repository.QACommentRepository;
import rm.backend.dto.innerDto.QACommentDto;

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

        if (itemQA.isAnswer()){
            throw new IllegalArgumentException("이미 답변이 달려있습니다.");
        }

        QAComment qaComment = new QAComment(admin, itemQA, request);

        qaCommentRepository.save(qaComment);

        itemQA.saveAnswer();
    }


    // 문의 답변 수정버튼 클릭시 해당 데이터 가져와서 반환
    @Transactional
    public QACommentDto.UpdateResponse getQAComment(Long id, Long qaCommentId) {

        QAComment qaComment = qaCommentRepository.findById(qaCommentId).orElseThrow(IllegalArgumentException::new);

        QACommentDto.UpdateResponse qaCommentDto = new QACommentDto.UpdateResponse(qaComment);

        return qaCommentDto;
    }


    @Transactional
    public void updateQAComment(Long id, QACommentDto.UpdateRequest request) {

        Admin admin = adminRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        QAComment qaComment = qaCommentRepository.findById(request.getQaCommentId()).orElseThrow(IllegalArgumentException::new);

        qaComment.update(admin, request);
    }


    // 문의 답변 삭제
    @Transactional
    public void deleteQAComment(Long qaCommentId) {

        QAComment qaComment = qaCommentRepository.findById(qaCommentId).orElseThrow(IllegalArgumentException::new);

        qaCommentRepository.delete(qaComment);

        ItemQA itemQA = itemQARepository.findById(qaComment.getItemQA().getItemQAId()).orElseThrow(IllegalArgumentException::new);

        itemQA.deleteAnswer();
    }


}

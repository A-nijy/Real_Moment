package rm.backend.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.Admin;
import rm.backend.domain.entity.Comment;
import rm.backend.domain.entity.OneOnOne;
import rm.backend.domain.repository.AdminRepository;
import rm.backend.domain.repository.CommentRepository;
import rm.backend.domain.repository.OneOnOneRepository;
import rm.backend.dto.innerDto.CommentDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCommentService {

    private final CommentRepository commentRepository;
    private final AdminRepository adminRepository;
    private final OneOnOneRepository oneOnOneRepository;



    // 1대1 문의 답변 작성
    @Transactional
    public void createComment(Long id, CommentDto.Request request) {

        Admin admin = adminRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        OneOnOne oneOnOne = oneOnOneRepository.findById(request.getOneOnOneId()).orElseThrow(IllegalArgumentException::new);

        if (oneOnOne.isAnswer()){
            throw new IllegalArgumentException("이미 답변이 달려있습니다.");
        }

        Comment comment = new Comment(oneOnOne, admin, request);

        commentRepository.save(comment);

        oneOnOne.saveIsAnswer();
    }


    // 1대1 문의 답변 수정 버튼 (가져오기)
    @Transactional
    public CommentDto.UpdateResponse getComment(Long id, Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);

        CommentDto.UpdateResponse commentDto = new CommentDto.UpdateResponse(comment);

        return commentDto;
    }


    // 1대1 문의 답변 수정
    @Transactional
    public void updateComment(Long id, CommentDto.UpdateRequest request) {

        Admin admin = adminRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Comment comment = commentRepository.findById(request.getCommentId()).orElseThrow(IllegalArgumentException::new);

        comment.updateComment(admin, request);
    }


    // 1대1 문의 답변 삭제
    @Transactional
    public void deleteComment(Long id, Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);

        commentRepository.delete(comment);

        OneOnOne oneOnOne = oneOnOneRepository.findById(comment.getOneOnOne().getOneOnOneId()).orElseThrow(IllegalArgumentException::new);

        oneOnOne.deleteIsAnswer();
    }
}

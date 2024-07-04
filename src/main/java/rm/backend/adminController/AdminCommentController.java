package rm.backend.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import rm.backend.adminService.AdminCommentService;
import rm.backend.dto.innerDto.CommentDto;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AdminCommentController {

    private final AdminCommentService adminCommentService;


    // 1대1 문의 답변 작성
    @PostMapping("/admin/{id}/comment")
    public String createComment(@PathVariable Long id, @RequestBody CommentDto.Request request){

        adminCommentService.createComment(id, request);

        return "답변 완료";
    }


    // 1대1 문의 답변 수정 버튼 (가져오기)
    @GetMapping("/admin/{id}/comment")
    public CommentDto.UpdateResponse getComment(@PathVariable Long id, @RequestParam("commentId") Long commentId){

        CommentDto.UpdateResponse commentDto = adminCommentService.getComment(id, commentId);

        return commentDto;
    }


    // 1대1 문의 답변 수정
    @PatchMapping("/admin/{id}/comment")
    public String updateComment(@PathVariable Long id, @RequestBody CommentDto.UpdateRequest request){

        adminCommentService.updateComment(id, request);

        return "답변 수정 완료";
    }


    // 1대1 문의 답변 삭제
    @DeleteMapping("/admin/{id}/comment")
    public String deleteComment(@PathVariable Long id, @RequestParam("commentId") Long commentId){

        adminCommentService.deleteComment(id, commentId);

        return "답변 삭제 완료";
    }
}

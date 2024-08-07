package rm.backend.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import rm.backend.adminService.AdminQACommentService;
import rm.backend.dto.innerDto.QACommentDto;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AdminQACommentController {

    private final AdminQACommentService adminQACommentService;


    // 상품 문의 댓글 작성
    @PostMapping("/admin/{id}/QAComment")
    public String saveQAComment(@PathVariable Long id, @RequestBody QACommentDto.SaveRequest request){

        adminQACommentService.saveQAComment(id, request);

        return "상품 문의 댓글 작성 완료!";
    }


    // 상품 문의 댓글 수정 버튼 클릭시 해당 데이터 가져옴
    @GetMapping("/admin/{id}/QAComment/data")
    public QACommentDto.UpdateResponse getQAComment(@PathVariable Long id, @RequestParam("qaCommentId") Long qaCommentId){

        QACommentDto.UpdateResponse qaCommentDto = adminQACommentService.getQAComment(id, qaCommentId);

        return qaCommentDto;
    }



    // 상품 문의 댓글 수정
    @PatchMapping("admin/{id}/QAComment")
    public String updateQAComment(@PathVariable Long id, @RequestBody QACommentDto.UpdateRequest request){

        adminQACommentService.updateQAComment(id, request);

        return "상품 문의 댓글 수정 완료!";
    }


    // 상품 문의 댓글 삭제
    @DeleteMapping("admin/{id}/QAComment")
    public String deleteQAComment(@PathVariable Long id, @RequestParam("qaCommentId") Long qaCommentId){

        adminQACommentService.deleteQAComment(qaCommentId);

        return "상품 문의 댓글 삭제 완료!";
    }
}

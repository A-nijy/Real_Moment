package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.adminService.AdminQACommentService;
import project1.shop.dto.innerDto.QACommentDto;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminQACommentController {

    private final AdminQACommentService adminQACommentService;


    // 상품 문의 댓글 작성
    @PostMapping("/admin/{id}/QAComment")
    public String saveQAComment(@PathVariable Long id, @RequestBody QACommentDto.SaveRequest request){

        adminQACommentService.saveQAComment(id, request);

        return "상품 문의 댓글 작성 완료!";
    }


    // 상품 문의 댓글 수정
    @PatchMapping("admin/{id}/QAComment")
    public String updateQAComment(@PathVariable Long id, @RequestBody QACommentDto.UpdateRequest request){

        adminQACommentService.updateQAComment(id, request);

        return "상품 문의 댓글 수정 완료!";
    }


    // 상품 문의 댓글 삭제
    @DeleteMapping("admin/QAComment")
    public String deleteQAComment(@RequestParam("qaCommentId") Long qaCommentId){

        adminQACommentService.deleteQAComment(qaCommentId);

        return "상품 문의 댓글 삭제 완료!";
    }
}

package rm.backend.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import rm.backend.adminService.AdminCategoryService;
import rm.backend.dto.innerDto.CategoryDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;


    // 카테고리 목록 조회
    @GetMapping("/admin/category/view")
    public List<CategoryDto.Response> showCategory(){

        List<CategoryDto.Response> categoryDto = adminCategoryService.showCategory();

        return categoryDto;
    }


    // 카테고리 추가
    @PostMapping("/admin/category")
    public String saveCategory(@RequestBody CategoryDto.CreateRequest request){

        adminCategoryService.saveCategory(request);

        return "카테고리 추가 완료!";
    }


    // 카테고리 수정
    @PatchMapping("/admin/category")
    public String updateCategory(@RequestBody CategoryDto.UpdateRequest request){

        adminCategoryService.updateCategory(request);

        return "카테고리 수정 완료!";
    }


    // 카테고리 삭제
    @DeleteMapping("/admin/category")
    public String deleteCategory(@RequestParam("categoryId") Long categoryId){

        adminCategoryService.deleteCategory(categoryId);

        return "카테고리 삭제 완료";
    }
}

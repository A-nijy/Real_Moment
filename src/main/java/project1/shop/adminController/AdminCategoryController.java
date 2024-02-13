package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.adminService.AdminCategoryService;
import project1.shop.dto.innerDto.CategoryDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;


    // 카테고리 목록 조회
    @GetMapping("/admin/category")
    public List<CategoryDto.Response> showCategory(){

        List<CategoryDto.Response> categoryDto = adminCategoryService.showCategory();

        return categoryDto;
    }


    // 카테고리 추가
    @PostMapping("/admin/category/{id}")
    public String saveCategory(@PathVariable Long id, @RequestBody CategoryDto.CreateRequest request){

        adminCategoryService.saveCategory(id, request);

        return "카테고리 추가 완료!";
    }


    // 카테고리 수정
    @PatchMapping("/admin/category/{id}")
    public String updateCategory(@PathVariable Long id, @RequestBody CategoryDto.UpdateRequest request){

        adminCategoryService.updateCategory(id, request);

        return "카테고리 수정 완료!";
    }
}

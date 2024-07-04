package rm.backend.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rm.backend.dto.innerDto.CategoryDto;
import rm.backend.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;


    // 카테고리 목록 조회
    @GetMapping("/category")
    public List<CategoryDto.Response> showCategory(){

        List<CategoryDto.Response> categoryDto = categoryService.showCategory();

        return categoryDto;
    }
}

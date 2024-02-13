package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Category;
import project1.shop.domain.repository.CategoryRepository;
import project1.shop.dto.innerDto.CategoryDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryService {

    private final CategoryRepository categoryRepository;


    // 카테고리 목록 조회
    @Transactional
    public List<CategoryDto.Response> showCategory() {

        List<Category> category = categoryRepository.findAll();

        List<CategoryDto.Response> categoryDto = category.stream()
                .map(CategoryDto.Response::new)
                .collect(Collectors.toList());

        return categoryDto;
    }


    // 카테고리 추가
    @Transactional
    public void saveCategory(Long id, CategoryDto.CreateRequest request) {

        Category category;

        if(request.getParentId() != null){
            Category parentCategory = categoryRepository.findById(request.getParentId()).orElseThrow(IllegalArgumentException::new);

            category = new Category(request, parentCategory);
        } else {
            category = new Category(request);
        }

        categoryRepository.save(category);
    }


    // 카테고리 수정
    @Transactional
    public void updateCategory(Long id, CategoryDto.UpdateRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(IllegalArgumentException::new);

        Category parentCategory;

        if(request.getParentId() != null){
            parentCategory = categoryRepository.findById(request.getParentId()).orElseThrow(IllegalArgumentException::new);
        } else {
            parentCategory = null;
        }

        category.update(request, parentCategory);
    }
}

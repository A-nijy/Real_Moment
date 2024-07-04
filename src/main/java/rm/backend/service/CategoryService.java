package rm.backend.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.Category;
import rm.backend.domain.repository.CategoryRepository;
import rm.backend.dto.innerDto.CategoryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;


    // 카테고리 목록 조회
    @Transactional
    public List<CategoryDto.Response> showCategory() {

        List<Category> parentCatergoryList = categoryRepository.findByParent(null);

        List<CategoryDto.Response> categoryList = new ArrayList<>();

        for (Category parentCategory : parentCatergoryList){

            categoryList.add(getChildcategory(parentCategory));
        }

        return categoryList;
    }

    // 특정 자식 카테고리 리스트 반환용 (부모카테고리 매개변수)
    public CategoryDto.Response getChildcategory(Category category) {

        List<Category> categoryList = categoryRepository.findByParent(category);

        List<CategoryDto.ChildCategory> childCategoryList = categoryList.stream()
                .map(CategoryDto.ChildCategory::new)
                .collect(Collectors.toList());

        CategoryDto.Response categoryDto = new CategoryDto.Response(category, childCategoryList);

        return categoryDto;
    }
}

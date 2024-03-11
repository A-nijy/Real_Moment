package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Category;
import project1.shop.domain.entity.Item;
import project1.shop.domain.repository.CategoryRepository;
import project1.shop.domain.repository.ItemRepository;
import project1.shop.dto.innerDto.CategoryDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;


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
    public void saveCategory(CategoryDto.CreateRequest request) {

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
    public void updateCategory(CategoryDto.UpdateRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(IllegalArgumentException::new);

        Category parentCategory;

        if (category.getParent() == null && request.getParentId() != null){
            throw new IllegalArgumentException("부모 카테고리 -> 자식 카테고리 변경이 불가능합니다.");
        } else if (category.getParent() != null && request.getParentId() == null){
            throw new IllegalArgumentException("자식 카테고리 -> 부모 카테고리 변경이 불가능합니다.");
        }

        if(request.getParentId() != null){
            parentCategory = categoryRepository.findById(request.getParentId()).orElseThrow(IllegalArgumentException::new);
        } else {
            parentCategory = null;
        }

        category.update(request, parentCategory);
    }


    // 카테고리 삭제
    @Transactional
    public void deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);

        // 만약 자식 카테고리라면?
        if (category.getParent() != null){

            List<Item> items = itemRepository.findByCategory_CategoryId(categoryId);

            // 해당 카테고리의 상품이 존재하면?
            if (!(items == null || items.isEmpty())){

                throw new IllegalArgumentException("해당 카테고리의 상품을 모두 제거해주세요");
            }

            categoryRepository.delete(category);

        } else {

            List<Category> categories = categoryRepository.findByParent(category);

            if (!(categories == null || categories.isEmpty())){

                throw new IllegalArgumentException("해당 카테고리의 자식 카테고리를 모두 제거해주세요");
            } else {

                categoryRepository.delete(category);
            }
        }



    }
}

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

import java.util.ArrayList;
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


    // 카테고리 추가
    @Transactional
    public void saveCategory(CategoryDto.CreateRequest request) {

        Category category;

        // 자식 카테고리를 추가한다면?
        if(request.getParentId() != null){
            Category parentCategory = categoryRepository.findById(request.getParentId()).orElseThrow(IllegalArgumentException::new);

            // 대상 카테고리가 부모카테고리가 아니고 이미 자식카테고리라면?
            if(parentCategory.getParent() != null){
                throw new IllegalArgumentException("자식 카테고리에 자식카테고리를 추가할 수 없습니다.");
            }

            category = new Category(request, parentCategory);

        // 부모 카테고리를 추가한다면?
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

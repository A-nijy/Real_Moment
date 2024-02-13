package project1.shop.service;


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
public class CategoryService {

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
}

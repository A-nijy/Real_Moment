package rm.backend.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rm.backend.domain.entity.Category;

import java.util.List;

public class CategoryDto {


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CreateRequest{
        private String name;
        private Long parentId = null;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateRequest{
        private Long categoryId;
        private String name;
        private Long parentId;
    }


    // 카테고리 조회 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response{
        private Long categoryId;
        private String name;
        private Long parentId;

        private List<ChildCategory> child;


        public Response(Category category, List<ChildCategory> childCategory){
            categoryId = category.getCategoryId();
            name = category.getName();
            parentId = (category.getParent() != null) ? category.getParent().getCategoryId() : null;
            child = childCategory;
        }
    }


    // 자식 카테고리 목록
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ChildCategory{
        private Long categoryId;
        private String name;
        private Long parentId;


        public ChildCategory(Category category){
            categoryId = category.getCategoryId();
            name = category.getName();
            parentId = (category.getParent() != null) ? category.getParent().getCategoryId() : null;
        }
    }
}

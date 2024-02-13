package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.Category;

public class CategoryDto {


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CreateRequest{
        private String name;
        private Long parentId;
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


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response{
        private Long categoryId;
        private String name;
        private Long parentId;


        public Response(Category category){
            categoryId = category.getCategoryId();
            name = category.getName();
            parentId = (category.getParent() != null) ? category.getParent().getCategoryId() : null;
        }
    }
}

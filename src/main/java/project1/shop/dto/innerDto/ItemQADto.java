package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.ItemQA;

import java.time.LocalDateTime;

public class ItemQADto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ItemQAResponse {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime writtenDate;
        private LocalDateTime editedDate;


        public ItemQAResponse(ItemQA itemQA){
            id = itemQA.getItemQAId();
            title = itemQA.getTitle();
            content = itemQA.getContent();
            writtenDate = itemQA.getWrittenDate();
            editedDate = itemQA.getEditedDate();
        }
    }
}

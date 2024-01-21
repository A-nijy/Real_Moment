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
        private String nickname;
        private String title;
        private String content;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;


        public ItemQAResponse(ItemQA itemQA){
            id = itemQA.getItemQAId();
            nickname = itemQA.getMember().getNickname();
            title = itemQA.getTitle();
            content = itemQA.getContent();
            createdDate = itemQA.getCreatedDate();
            lastModifiedDate = itemQA.getLastModifiedDate();
        }
    }
}

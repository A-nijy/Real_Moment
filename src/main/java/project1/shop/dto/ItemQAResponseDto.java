package project1.shop.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.ItemQA;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemQAResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime writtenDate;
    private LocalDateTime editedDate;


    public ItemQAResponseDto(ItemQA itemQA){
        id = itemQA.getItemQAId();
        title = itemQA.getTitle();
        content = itemQA.getContent();
        writtenDate = itemQA.getWrittenDate();
        editedDate = itemQA.getEditedDate();
    }
}

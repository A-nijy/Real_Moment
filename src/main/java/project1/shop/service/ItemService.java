package project1.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.ItemQA;
import project1.shop.domain.entity.Review;
import project1.shop.domain.repository.ItemQARepository;
import project1.shop.domain.repository.ItemRepository;
import project1.shop.domain.repository.ReviewRepository;
import project1.shop.dto.*;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;
    private final ItemQARepository itemQARepository;


    @Transactional
    public List<ItemsResponseDto> showItems(Long id) {

        log.info("서비스 시작 -> repository에서 데이터 찾기");
        List<Item> items = itemRepository.findByCategory_CategoryId(id);

        if(items.isEmpty()){
            log.info("데이터가 존재하지 않음 -> 예외 발생");
            throw new IllegalArgumentException();
        }

        log.info("repositroy에서 데이터 찾음 -> entity를 dto로 변환");
        List<ItemsResponseDto> itemsDto = items.stream()
                .map(ItemsResponseDto::new)
                .collect(Collectors.toList());

        log.info("dto를 컨트롤러에 반환");
        return itemsDto;
    }

    @Transactional
    public ItemViewResponseDto showItem(Long id) {

        Item item = itemRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        List<Review> reviews = reviewRepository.findByItem_ItemId(id);

        List<ItemQA> itemQAs = itemQARepository.findByItem_ItemId(id);

        ItemResponseDto itemDto = new ItemResponseDto(item);

        List<ReviewResponseDto> reviewDto = reviews.stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());

        List<ItemQAResponseDto> itemQADto = itemQAs.stream()
                .map(ItemQAResponseDto::new)
                .collect(Collectors.toList());

        ItemViewResponseDto itemViewDto = new ItemViewResponseDto(itemDto, reviewDto, itemQADto);

        return itemViewDto;

    }
}

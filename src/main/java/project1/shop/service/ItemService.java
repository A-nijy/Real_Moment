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
import project1.shop.dto.innerDto.ItemDto;
import project1.shop.dto.innerDto.ItemQADto;
import project1.shop.dto.innerDto.ReviewDto;

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
    public List<ItemDto.SimpleItemResponse> showItems(Long id) {

        log.info("서비스 시작 -> repository에서 데이터 찾기");
        List<Item> items = itemRepository.findByCategory_CategoryId(id);

        if(items.isEmpty()){
            log.info("데이터가 존재하지 않음 -> 예외 발생");
            throw new IllegalArgumentException();
        }

        log.info("repositroy에서 데이터 찾음 -> entity를 dto로 변환");
        List<ItemDto.SimpleItemResponse> itemsDto = items.stream()
                .map(ItemDto.SimpleItemResponse::new)
                .collect(Collectors.toList());

        log.info("dto를 컨트롤러에 반환");
        return itemsDto;
    }

    @Transactional
    public ItemDto.ItemPageResponse showItem(Long id) {

        Item item = itemRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        List<Review> reviews = reviewRepository.findByItem_ItemId(id);

        List<ItemQA> itemQAs = itemQARepository.findByItem_ItemId(id);

        ItemDto.FullItemResponse itemDto = new ItemDto.FullItemResponse(item);

        List<ReviewDto.ReviewResponse> reviewDto = reviews.stream()
                .map(ReviewDto.ReviewResponse::new)
                .collect(Collectors.toList());

        List<ItemQADto.ItemQAResponse> itemQADto = itemQAs.stream()
                .map(ItemQADto.ItemQAResponse::new)
                .collect(Collectors.toList());

        ItemDto.ItemPageResponse itemPageDto = new ItemDto.ItemPageResponse(itemDto, reviewDto, itemQADto);

        return itemPageDto;

    }
}

package project1.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import project1.shop.dto.innerDto.SearchDto;

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
    public List<ItemDto.SimpleItemResponse> showItems(SearchDto.Items request) {


        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 9);

        Page<Item> items = itemRepository.searchPageSimple(request, pageRequest);

        List<ItemDto.SimpleItemResponse> itemsDto = items.stream()
                                                        .map(ItemDto.SimpleItemResponse::new)
                                                        .collect(Collectors.toList());

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

package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Category;
import project1.shop.domain.entity.Item;
import project1.shop.domain.repository.CategoryRepository;
import project1.shop.domain.repository.ItemRepository;
import project1.shop.domain.repository.WishRepository;
import project1.shop.dto.innerDto.ItemDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;


    // 상품 목록 조회
    @Transactional
    public ItemDto.SimpleItemPageResponse showItems(SearchDto.Items request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 9);

        Page<Item> items = itemRepository.searchPageSimple(request, pageRequest);

        List<ItemDto.SimpleItemResponse> itemsDto = items.stream()
                .map(ItemDto.SimpleItemResponse::new)
                .collect(Collectors.toList());

        ItemDto.SimpleItemPageResponse itemPageDto = new ItemDto.SimpleItemPageResponse(itemsDto, items.getTotalPages(), request.getNowPage());

        return itemPageDto;
    }


    // 상품 상세 조회
    @Transactional
    public ItemDto.FullItemResponse showItem(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);

        ItemDto.FullItemResponse itemDto = new ItemDto.FullItemResponse(item);

        return itemDto;
    }


    // 상품 저장
    @Transactional
    public void saveItem(ItemDto.SaveRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(IllegalArgumentException::new);

        Item item = new Item(category, request);

        itemRepository.save(item);
    }


    // 상품 수정버튼 클릭시 해당 데이터 가져와서 반환
    @Transactional
    public ItemDto.UpdateResponse getItem(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);

        ItemDto.UpdateResponse itemDto = new ItemDto.UpdateResponse(item);

        return itemDto;
    }



    // 상품 수정
    @Transactional
    public void updateItem(ItemDto.UpdateRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(IllegalArgumentException::new);

        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        item.update(category, request);
    }


    // 상품 삭제
    @Transactional
    public void deleteItem(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);



        item.delete();
    }


}

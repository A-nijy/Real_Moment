package rm.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.*;
import rm.backend.domain.repository.*;
import rm.backend.dto.innerDto.*;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;
    private final ItemQARepository itemQARepository;
    private final QACommentRepository qaCommentRepository;
    private final ItemFileRepository itemFileRepository;
    private final CategoryRepository categoryRepository;


    // 상품 목록 조회
    @Transactional
    public ItemDto.SimpleItemPageResponse showItems(SearchDto.Items request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 12);

        Page<Item> items = itemRepository.searchPageSimple(request, pageRequest);

        List<ItemDto.SimpleItemResponse> itemsDto = items.stream()
                                                        .map(this::mapToDto)
                                                        .collect(Collectors.toList());

        ItemDto.SimpleItemPageResponse itemPageDto = new ItemDto.SimpleItemPageResponse(itemsDto, items.getTotalPages(), request.getNowPage());

        return itemPageDto;

    }

    // DTO 변환
    public ItemDto.SimpleItemResponse mapToDto(Item item){

        ItemFile itemFile = itemFileRepository.searchFirstMainImg(item).orElse(null);

        ItemDto.SimpleItemResponse simpleItemDto = null;

        if (itemFile == null){
            simpleItemDto = new ItemDto.SimpleItemResponse(item, null);
        } else {
            simpleItemDto = new ItemDto.SimpleItemResponse(item, itemFile.getS3File().getFileUrl());
        }

        return simpleItemDto;
    }


    // 상품 상세 조회
    @Transactional
    public ItemDto.FullItemResponse showItem(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);

        if(item.isDelete() == true){
            throw new IllegalArgumentException("해당 상품은 조회할 수 없습니다.");
        }

        List<S3File> mainImgList = itemFileRepository.searchMainImgList(item);

        List<S3File> serveImgList = itemFileRepository.searchServeImgList(item);

        List<S3Dto.ImgDataResponse> imgDataDataListResponse = mainImgList.stream()
                .map(S3Dto.ImgDataResponse::new)
                .collect(Collectors.toList());

        List<S3Dto.ImgDataResponse> subImgDataResponseList = serveImgList.stream()
                .map(S3Dto.ImgDataResponse::new)
                .collect(Collectors.toList());

        ItemDto.FullItemResponse itemDto = new ItemDto.FullItemResponse(item, imgDataDataListResponse, subImgDataResponseList);

        return itemDto;
    }

}

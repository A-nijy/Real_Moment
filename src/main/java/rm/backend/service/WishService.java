package rm.backend.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.Item;
import rm.backend.domain.entity.ItemFile;
import rm.backend.domain.entity.Member;
import rm.backend.domain.entity.Wish;
import rm.backend.domain.repository.ItemFileRepository;
import rm.backend.domain.repository.ItemRepository;
import rm.backend.domain.repository.MemberRepository;
import rm.backend.domain.repository.WishRepository;
import rm.backend.dto.innerDto.ItemDto;
import rm.backend.dto.innerDto.SearchDto;
import rm.backend.dto.innerDto.WishDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemFileRepository itemFileRepository;


    // 찜 목록 조회
    @Transactional
    public WishDto.WishListPageResponse showWishList(Long id, SearchDto.Page request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 10);

        Page<Wish> wish = wishRepository.searchPageWishList(id, pageRequest);

        List<WishDto.WishListResponse> wishListResponses = wish.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        WishDto.WishListPageResponse wishListPageDto = new WishDto.WishListPageResponse(wishListResponses, wish.getTotalPages(), request.getNowPage());

        return wishListPageDto;
    }

    // WishList엔티티 -> WishListDto 변환 과정 (WishListDto 내부에 ItemDto가 있는데 WishList엔티티의 Item을 ItemDto로 변환)
    public WishDto.WishListResponse mapToDto(Wish wish) {

        ItemFile itemFile = itemFileRepository.searchFirstMainImg(wish.getItem()).orElse(null);

        ItemDto.SimpleItemResponse simpleItemDto = null;

        if (itemFile == null){
            simpleItemDto = new ItemDto.SimpleItemResponse(wish.getItem(), null);
        } else {
            simpleItemDto = new ItemDto.SimpleItemResponse(wish.getItem(), itemFile.getS3File().getFileUrl());
        }

        WishDto.WishListResponse wishListResponse = new WishDto.WishListResponse(wish.getWishId(), simpleItemDto);

        return wishListResponse;
    }


    // 찜 추가
    @Transactional
    public void saveWishList(Long id, WishDto.WishRequest request) {

        Wish wish = wishRepository.findByMember_memberIdAndItem_ItemId(id, request.getItemId()).orElse(null);

        if (wish != null){
            throw new IllegalArgumentException("이미 찜에 등록되어 있습니다.");
        }

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        wishRepository.save(new Wish(member, item));
    }

    // 찜 삭제
    @Transactional
    public void deleteWishList(Long memberId, Long wishId) {

        Wish wish = wishRepository.findByWishIdAndMember_MemberId(wishId, memberId).orElseThrow(IllegalArgumentException::new);

        wishRepository.delete(wish);
    }
}

package project1.shop.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.Member;
import project1.shop.domain.entity.Wish;
import project1.shop.domain.repository.ItemRepository;
import project1.shop.domain.repository.MemberRepository;
import project1.shop.domain.repository.WishRepository;
import project1.shop.dto.innerDto.ItemDto;
import project1.shop.dto.innerDto.WishDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    // 찜 목록 조회
    @Transactional
    public List<WishDto.WishListResponse> showWishList(Long id) {

        List<Wish> wish = wishRepository.findByMember_MemberId(id);

        List<WishDto.WishListResponse> wishListResponses = wish.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return wishListResponses;
    }

    // WishList엔티티 -> WishListDto 변환 과정 (WishListDto 내부에 ItemDto가 있는데 WishList엔티티의 Item을 ItemDto로 변환)
    public WishDto.WishListResponse mapToDto(Wish wish) {

        ItemDto.SimpleItemResponse simpleItemDto = new ItemDto.SimpleItemResponse(wish.getItem());

        WishDto.WishListResponse wishListResponse = new WishDto.WishListResponse(wish.getWishId(), simpleItemDto);

        return wishListResponse;
    }


    // 찜 추가
    @Transactional
    public void saveWishList(Long id, WishDto.WishRequest request) {

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

package project1.shop.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.Member;
import project1.shop.domain.entity.WishList;
import project1.shop.domain.repository.ItemRepository;
import project1.shop.domain.repository.MemberRepository;
import project1.shop.domain.repository.WishListRepository;
import project1.shop.dto.innerDto.ItemDto;
import project1.shop.dto.innerDto.WishListDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishListService {

    private final WishListRepository wishListRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public List<WishListDto.WishListResponse> showWishList(Long id) {

        List<WishList> wishList = wishListRepository.findByMember_MemberId(id);

        List<WishListDto.WishListResponse> wishListResponses = wishList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return wishListResponses;
    }






    // WishList엔티티 -> WishListDto 변환 과정 (WishListDto 내부에 ItemDto가 있는데 WishList엔티티의 Item을 ItemDto로 변환)

    public WishListDto.WishListResponse mapToDto(WishList wishList) {

        ItemDto.SimpleItemResponse simpleItemDto = new ItemDto.SimpleItemResponse(wishList.getItem());

        WishListDto.WishListResponse wishListResponse = new WishListDto.WishListResponse(wishList.getWishListId(), simpleItemDto);

        return wishListResponse;
    }

    @Transactional
    public void saveWishList(Long id, Long itemId) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Item item = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);

        wishListRepository.save(new WishList(member, item));
    }

    @Transactional
    public void deleteWishList(Long memberId, Long wishListId) {

        WishList wishList = wishListRepository.findByWishListIdAndMember_MemberId(wishListId, memberId).orElseThrow(IllegalArgumentException::new);

        wishListRepository.delete(wishList);
    }
}

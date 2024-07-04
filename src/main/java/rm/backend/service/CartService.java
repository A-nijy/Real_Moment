package rm.backend.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.Cart;
import rm.backend.domain.entity.Item;
import rm.backend.domain.entity.ItemFile;
import rm.backend.domain.entity.Member;
import rm.backend.domain.repository.CartRepository;
import rm.backend.domain.repository.ItemFileRepository;
import rm.backend.domain.repository.ItemRepository;
import rm.backend.domain.repository.MemberRepository;
import rm.backend.dto.innerDto.CartDto;
import rm.backend.dto.innerDto.ItemDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final ItemFileRepository itemFileRepository;


    // 장바구니 목록 조회
    @Transactional
    public List<CartDto.CartResponse> showCart(Long id) {

        List<Cart> carts = cartRepository.findByMember_MemberIdOrderByCartIdDesc(id);

        List<CartDto.CartResponse> cartsDto = carts.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return cartsDto;

    }

    // Cart엔티티 -> CartDto 변환 과정 (CartDto 내부에 ItemDto가 있는데 Cart엔티티의 Item을 ItemDto로 변환)
    public CartDto.CartResponse mapToDto(Cart cart) {

        ItemFile itemFile = itemFileRepository.searchFirstMainImg(cart.getItem()).orElse(null);

        ItemDto.SimpleItemResponse simpleItemDto = null;

        if (itemFile == null){
            simpleItemDto = new ItemDto.SimpleItemResponse(cart.getItem(), null);
        } else {
            simpleItemDto = new ItemDto.SimpleItemResponse(cart.getItem(), itemFile.getS3File().getFileUrl());
        }

        CartDto.CartResponse cartResponse = new CartDto.CartResponse(cart.getCartId(), simpleItemDto, cart.getCount());

        return cartResponse;
    }


    // 장바구니 추가
    @Transactional
    public void saveCart(Long memberId, CartDto.CartSaveRequest request) {

        Cart cartCheck = cartRepository.findByMember_MemberIdAndItem_ItemId(memberId, request.getItemId()).orElse(null);

        if(cartCheck != null){
            throw new IllegalArgumentException("이미 장바구니에 존재한 상품입니다.");
        }

        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        Cart cart = new Cart(member, item, request.getCount());

        cartRepository.save(cart);
    }


    // 장바구니 삭제
    @Transactional
    public void deleteCart(Long id, Long cartId) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(IllegalArgumentException::new);

        cartRepository.delete(cart);
    }


    // 장바구니 수량 수정
    @Transactional
    public void updateStockCart(Long id, CartDto.CartUpdateCountRequest request) {

        Cart cart = cartRepository.findById(request.getCartId()).orElseThrow(IllegalArgumentException::new);

        cart.updateCountCart(request.getCount());
    }
}
